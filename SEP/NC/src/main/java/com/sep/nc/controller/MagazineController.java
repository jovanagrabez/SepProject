package com.sep.nc.controller;

import com.sep.nc.entity.Magazine;
import com.sep.nc.entity.MagazinePurchase;
import com.sep.nc.entity.User;
import com.sep.nc.entity.dto.BuyProductDto;
import com.sep.nc.entity.dto.FinishedMagazineOrderDto;
import com.sep.nc.entity.dto.ShowMagazinesDto;
import com.sep.nc.entity.dto.UserDto;
import com.sep.nc.entity.enumeration.PurchaseStatus;
import com.sep.nc.entity.enumeration.TypeOfProduct;
import com.sep.nc.repository.MagazinePurchasesRepository;
import com.sep.nc.repository.UserRepository;
import com.sep.nc.service.MagazineService;
import com.sep.nc.service.UserService;
import com.sep.nc.service.impl.UtilityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "api/magazine")
public class MagazineController {

    private final MagazineService magazineService;
    private final RestTemplate restTemplate;
    private final UserService userService;
    private final MagazinePurchasesRepository magazinePurchasesRepository;
    private final UserRepository userRepository;

    private static final String KP_SERVICE_URI= "https://localhost:8762/koncentrator_placanja/api";

    @Autowired
    public MagazineController(MagazineService magazineService, RestTemplate restTemplate, UserService userService, MagazinePurchasesRepository magazinePurchasesRepository, UserRepository userRepository) {
        this.magazineService = magazineService;
        this.restTemplate = restTemplate;
        this.userService = userService;
        this.magazinePurchasesRepository = magazinePurchasesRepository;
        this.userRepository = userRepository;
    }


    @GetMapping
    public ResponseEntity<List<ShowMagazinesDto>> getAllMagazines() {
        return new ResponseEntity<>(magazineService.getAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/buy/{magazineId}")
    public Map<String, String> buyMagazine(@PathVariable Long magazineId, @RequestHeader(value = "Authorization") String authorization) throws Exception {

        String email = UtilityService.getEmailFromToken(authorization);
        if (email == null) {
            throw new Exception("Jwt error");
        }
        Magazine magazine = this.magazineService.getById(magazineId);

        MagazinePurchase magazinePurchase = this.magazinePurchasesRepository.save(new MagazinePurchase(magazine, PurchaseStatus.New));
        User user = userRepository.findUserByEmail(email);
        user.getMagazinePurchases().add(magazinePurchase);
        userService.saveUser(user);


        BuyProductDto buyProductDto = new BuyProductDto(magazineId, magazine.getName(), email, magazine.getPrice(), TypeOfProduct.magazine, magazinePurchase.getId());

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity requestEntity = new HttpEntity<>(buyProductDto, requestHeaders);

//        URLEncoder.encode(url, StandardCharsets.UTF_8.toString());
        ResponseEntity<String> resp = restTemplate.postForEntity(KP_SERVICE_URI + "/transaction/generate_url",requestEntity, String.class);

        HashMap<String, String> map = new HashMap<>();
        map.put("url", resp.getBody());
        return map;
    }

    @PostMapping(value = "/finish")
    public void successOrder(@RequestBody FinishedMagazineOrderDto finishedMagazineOrderDto) {
        log.info("Magazine access changed for user: "+finishedMagazineOrderDto.getEmail() +" and purchase id: "+
                finishedMagazineOrderDto.getPurchaseId() + " to " + finishedMagazineOrderDto.getPurchaseStatus());
        this.userService.setMagazinePurchaseStatus(finishedMagazineOrderDto.getEmail(),
                finishedMagazineOrderDto.getPurchaseId(), finishedMagazineOrderDto.getPurchaseStatus());
    }
}
