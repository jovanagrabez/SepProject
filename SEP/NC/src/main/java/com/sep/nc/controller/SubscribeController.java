package com.sep.nc.controller;

import com.sep.nc.entity.Magazine;
import com.sep.nc.entity.dto.SubscribeDto;
import com.sep.nc.service.MagazineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "api/")
public class SubscribeController {

    private final MagazineService magazineService;

    @Autowired
    private RestTemplate restTemplate;

    private static final String PP_SERVICE_URI= "https://localhost:8762/koncentrator_placanja/api/subscription/subscribe";

    public SubscribeController(MagazineService magazineService) {
        this.magazineService = magazineService;
    }

    @GetMapping(value = "/subscribe/{magazineId}")
    public Map<String, String> subscribeToPlan(@PathVariable Long magazineId, @RequestHeader(value = "Authorization") String authorization) {

        String response = this.restTemplate.getForObject(PP_SERVICE_URI + "/" + magazineId, String.class);

        HashMap<String, String> map = new HashMap<>();
        map.put("url", response);
        return map;
        /*HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity requestEntity = new HttpEntity<>(subscribeDto, requestHeaders);

        ResponseEntity<String> response = this.restTemplate.postForEntity(PP_SERVICE_URI, requestEntity, String.class);

        HashMap<String, String> map = new HashMap<>();
        map.put("url", response.getBody());
        return map;*/
       /* if (response.getStatusCode() == HttpStatus.OK) {
            return map;
        } else {
            return null;
        }*/
    }
}
