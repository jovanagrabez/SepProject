package com.sep.nc.controller;

import com.sep.nc.entity.MagazinePurchase;
import com.sep.nc.entity.dto.MagazinePurchaseDto;
import com.sep.nc.entity.dto.ShowMagazinesDto;
import com.sep.nc.entity.enumeration.PurchaseStatus;
import com.sep.nc.repository.MagazinePurchasesRepository;
import com.sep.nc.service.impl.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.ArrayList;
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
    public ResponseEntity<List<MagazinePurchaseDto>> getAllPurchases(@RequestHeader(value = "Authorization") String authorization) throws Exception {

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

            }
        }

        List<MagazinePurchaseDto> magazinePurchaseDtos = new ArrayList<>();
        for(MagazinePurchase purchase : magazinePurchases) {
            magazinePurchaseDtos.add(new MagazinePurchaseDto(new ShowMagazinesDto(purchase.getMagazine().getId(),
                    purchase.getMagazine().getName(), purchase.getMagazine().getISSNNumber(),
                    purchase.getMagazine().getScientificAreas(), purchase.getMagazine().getPaymentType(),
                    purchase.getMagazine().getPrice()), purchase.getStatus()));
        }
        return ResponseEntity.ok(magazinePurchaseDtos);
    }
}
