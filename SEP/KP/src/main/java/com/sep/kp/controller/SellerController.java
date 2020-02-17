package com.sep.kp.controller;

import com.sep.kp.model.DTO.CreateSellerDto;
import com.sep.kp.model.PaymentMethod;
import com.sep.kp.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin({"https://localhost:8762", "https://localhost:4201"})

@RequestMapping(value = "/api/seller")
public class SellerController {

    @Autowired
    private SellerService sellerService;


    @PostMapping(value = "/{userId}")
    public ResponseEntity showPage(@PathVariable Long userId, @RequestBody List<PaymentMethod> paymentMethods) {

        this.sellerService.newSellerPaymentMethods(paymentMethods, userId);
//        System.out.println("Date planted: " + bean.getBankName()); //in reality, you'd use a logger instead :)
        return ResponseEntity.ok().build();
    }


}
