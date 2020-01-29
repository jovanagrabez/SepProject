package com.sep.nc.controller;

import com.sep.nc.entity.MagazinePurchase;
import com.sep.nc.entity.enumeration.PurchaseStatus;
import com.sep.nc.repository.MagazinePurchasesRepository;
import com.sep.nc.service.impl.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "api/purchase")
public class MagazinePurchaseController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MagazinePurchasesRepository magazinePurchasesRepository;

    private static final String KP_SERVICE_URI= "https://localhost:8762/koncentrator_placanja/api/transaction/purchase-status/";

    @GetMapping
    public ResponseEntity<List<MagazinePurchase>> getAllPurchases(@RequestHeader(value = "Authorization") String authorization) throws Exception {

        String email = UtilityService.getEmailFromToken(authorization);
        if (email == null) {
            throw new Exception("Jwt error");
        }
        List<MagazinePurchase> magazinePurchases = this.magazinePurchasesRepository.findMagazinePurchaseByUser(email);

        for (int i = 0; i < magazinePurchases.size(); i++) {
            if (magazinePurchases.get(i).getStatus().equals(PurchaseStatus.New)) {
                // TODO pozvati kp za proveru statusa

                HttpHeaders requestHeaders = new HttpHeaders();
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

                HttpEntity requestEntity = new HttpEntity<>(requestHeaders);

                ResponseEntity resp = restTemplate.getForEntity(KP_SERVICE_URI+magazinePurchases.get(i).getId(), String.class, requestEntity);

                magazinePurchases.get(i).setStatus((PurchaseStatus) resp.getBody());
//                return new ResponseEntity(resp.getBody(), HttpStatus.OK);
            }
        }

//        HttpHeaders requestHeaders = new HttpHeaders();
//        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
//        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//
//        HttpEntity requestEntity = new HttpEntity<>(message, requestHeaders);
//
//        ResponseEntity resp = restTemplate.exchange(KP_SERVICE_URI, HttpMethod.POST,requestEntity, String.class);
//
//        return new ResponseEntity(resp.getBody(), HttpStatus.OK);
        return ResponseEntity.ok(magazinePurchases);
    }
}
