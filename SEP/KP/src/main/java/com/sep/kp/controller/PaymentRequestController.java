package com.sep.kp.controller;


import com.sep.kp.model.DTO.PaymentDTO;
import com.sep.kp.model.DTO.RequestDTO;
import com.sep.kp.model.PaymentRequest;
import com.sep.kp.service.PaymentRequestService;
import com.sep.kp.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/payment")
public class PaymentRequestController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private PaymentRequestService paymentRequestService;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping(value = "/pay-by-bank-card")
    public ResponseEntity<PaymentDTO> createPaymentRequest(@RequestBody @Valid RequestDTO requestDTO)  {

        PaymentRequest paymentRequest = paymentRequestService.createPaymentRequest(requestDTO.getClient(), requestDTO.getAmount());
        PaymentDTO paymentDataDTO =restTemplate.postForObject("https://localhost:8762/bank_service/api/get-payment-url",
                paymentRequest, PaymentDTO.class);

        return ResponseEntity.ok(paymentDataDTO);
    }

}
