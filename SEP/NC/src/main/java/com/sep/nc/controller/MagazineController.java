package com.sep.nc.controller;

import com.sep.nc.entity.Magazine;
import com.sep.nc.entity.User;
import com.sep.nc.entity.dto.BuyMagazineDto;
import com.sep.nc.service.MagazineService;
import com.sep.nc.service.UserService;
import com.sep.nc.service.impl.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

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
    public ResponseEntity buyMagazine(@PathVariable Long magazineId, @RequestHeader(value = "Authorization") String authorization) throws Exception {

        String email = UtilityService.getEmailFromToken(authorization);
        if (email == null) {
            throw new Exception("Jwt error");
        }
        Magazine magazine = this.magazineService.getById(magazineId);
        User user = this.userService.getUserByEmail(email);

        BuyMagazineDto buyMagazineDto = new BuyMagazineDto(magazineId, magazine.getName(), user.getId(), email);

        // TODO contact KP to get redirectUrl

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity requestEntity = new HttpEntity<>(buyMagazineDto, requestHeaders);

        ResponseEntity resp = restTemplate.exchange(KP_SERVICE_URI + "/generate_url", HttpMethod.POST,requestEntity, String.class);
        return resp;
    }
}
