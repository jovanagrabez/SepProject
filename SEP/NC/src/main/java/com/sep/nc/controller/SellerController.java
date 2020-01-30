package com.sep.nc.controller;

import com.sep.nc.entity.Magazine;
import com.sep.nc.entity.MagazinePurchase;
import com.sep.nc.entity.User;
import com.sep.nc.entity.enumeration.PurchaseStatus;
import com.sep.nc.service.UserService;
import com.sep.nc.service.impl.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Arrays;

@RestController
@RequestMapping(value = "/api/seller")
public class SellerController {

    @Autowired
    private UserService userService;

    private static final String KP_SERVICE_URI= "https://localhost:8762/koncentrator_placanja/api/seller";


    @GetMapping()
    public ResponseEntity<String> newSeller(@RequestHeader(value = "Authorization") String authorization) throws Exception {
        String email = UtilityService.getEmailFromToken(authorization);
        if (email == null) {
            throw new Exception("Jwt error");
        }

        User seller = userService.getUserByEmail(email);
        return ResponseEntity.ok("https://localhost:8762/koncentrator_placanja/api/seller/" + seller.getId());
    }
}
