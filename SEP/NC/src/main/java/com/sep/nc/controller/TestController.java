package com.sep.nc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.Arrays;

@RestController
@RequestMapping(value = "api/test")
public class TestController {

    @Autowired
    private RestTemplate restTemplate;

    private static final String KP_SERVICE_URI= "https://localhost:8762/koncentrator_placanja/api/test";

    @GetMapping(value = "/{message}")
    public ResponseEntity testMethod(@PathVariable String message) {

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity requestEntity = new HttpEntity<>(message, requestHeaders);

        ResponseEntity resp = restTemplate.exchange(KP_SERVICE_URI, HttpMethod.POST,requestEntity, String.class);

        return new ResponseEntity(resp.getBody(), HttpStatus.OK);
    }
}
