package com.sep.kp.controller;


import com.sep.kp.model.DTO.SubscribeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "api/subscription")
public class SubscribeController {
    private static final String PP_SERVICE_URI= "https://localhost:8762/paypal_service/api/plan/subscribe/";

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping(value = "/subscribe")
    public String subscribeToPlan(@RequestBody SubscribeDto subscribeDto) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity requestEntity = new HttpEntity<>(subscribeDto, requestHeaders);

        ResponseEntity<String> response = this.restTemplate.postForEntity(PP_SERVICE_URI, requestEntity, String.class);

        //HashMap<String, String> map = new HashMap<>();
        //map.put("url", response.getBody());
        return response.getBody();
    }
}
