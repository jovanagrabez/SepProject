package com.sep.nc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Arrays;

@RestController
@RequestMapping(value = "/api/seller")
public class SellerController {

    @Autowired
    private RestTemplate restTemplate;

    private static final String KP_SERVICE_URI= "https://localhost:8762/koncentrator_placanja/api/seller";


    @GetMapping()
    public void newSeller() {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity requestEntity = new HttpEntity<>(requestHeaders);

//        URLEncoder.encode(url, StandardCharsets.UTF_8.toString());
        ResponseEntity<RedirectView> resp = restTemplate.getForEntity(KP_SERVICE_URI+"/1", RedirectView.class, requestEntity);

//        return new RedirectView("https://localhost:8762/koncentrator_placanja/registerSeller.html");
    }
}
