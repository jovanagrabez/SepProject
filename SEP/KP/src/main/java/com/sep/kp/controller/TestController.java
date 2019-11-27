package com.sep.kp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@RestController
@RequestMapping(value = "api/test")
public class TestController {

    @Autowired
    private RestTemplate restTemplate;

    private static final String PayPal_SERVICE_URI= "https://localhost:8762/paypal_service/api/test";

    @PostMapping
    public ResponseEntity testMethod(@RequestBody String message) {

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity requestEntity = new HttpEntity<>(message, requestHeaders);

        ResponseEntity resp = restTemplate.exchange(PayPal_SERVICE_URI, HttpMethod.POST,requestEntity, String.class);

        return new ResponseEntity(resp.getBody(), HttpStatus.OK);
    }
}
