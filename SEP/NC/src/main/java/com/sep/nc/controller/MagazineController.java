package com.sep.nc.controller;

import com.sep.nc.entity.Magazine;
import com.sep.nc.entity.dto.BuyProductDto;
import com.sep.nc.entity.dto.FinishedMagazineOrderDto;
import com.sep.nc.entity.enumeration.PaymentType;
import com.sep.nc.entity.enumeration.TypeOfProduct;
import com.sep.nc.service.MagazineService;
import com.sep.nc.service.UserService;
import com.sep.nc.service.impl.UtilityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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

    private static final String KP_SERVICE_URI= "https://localhost:8762/koncentrator_placanja/api";

    @Autowired
    public MagazineController(MagazineService magazineService, RestTemplate restTemplate, UserService userService) {
        this.magazineService = magazineService;
        this.restTemplate = restTemplate;
        this.userService = userService;
    }


    @GetMapping
    public ResponseEntity<List<Magazine>> getAllMagazines() {
        return new ResponseEntity<>(magazineService.getAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/buy/{magazineId}")
    public Map<String, String> buyMagazine(@PathVariable Long magazineId, @RequestHeader(value = "Authorization") String authorization) throws Exception {

        String email = UtilityService.getEmailFromToken(authorization);
        if (email == null) {
            throw new Exception("Jwt error");
        }
        Magazine magazine = this.magazineService.getById(magazineId);

        BuyProductDto buyProductDto = new BuyProductDto(magazineId, magazine.getName(), email, magazine.getPrice(), TypeOfProduct.magazine);

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
        log.info("Magazine access allowed for user: "+finishedMagazineOrderDto.getEmail() +" and magazine id: "+finishedMagazineOrderDto.getMagazineId());
        this.userService.addBoughtMagazine(finishedMagazineOrderDto.getEmail(), finishedMagazineOrderDto.getMagazineId());
    }
}
