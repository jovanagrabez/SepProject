package com.sep.kp.service.impl;


import com.sep.kp.model.PaymentRequest;
import com.sep.kp.model.Seller;
import com.sep.kp.repository.PaymentRequestRepository;
import com.sep.kp.repository.SellerRepository;
import com.sep.kp.service.PaymentRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Service
public class PaymentRequestServiceImpl implements PaymentRequestService {

    private final Logger LOGGER = LoggerFactory.getLogger(PaymentRequestServiceImpl.class);

    @Autowired
    private PaymentRequestRepository paymentRequestRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Override
    public PaymentRequest createPaymentRequest(String clientID, double amount) {
        LOGGER.info("Finding seller: " );
        LOGGER.info("CLIENT ID: " );


        Seller foundSeller = sellerRepository.findSellerById(Long.parseLong(clientID));

        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setMerchantId(foundSeller.getMerchantId());
        paymentRequest.setMerchantPassword("123");

        Random random = new Random();
        paymentRequest.setMerchantOrderId((long)random.nextInt());
        paymentRequest.setAmount(amount);
        paymentRequest.setMerchantTimestamp(new Date());
        paymentRequest.setSuccessUrl("/success");
        paymentRequest.setErrorUrl("/error");
        paymentRequest.setFailedUrl("/fail");

        LOGGER.info("Created paymentRequest: " + paymentRequest);

        paymentRequestRepository.save(paymentRequest);

        return paymentRequest;
    }

    @Override
    public PaymentRequest getPaymentRequest(Long merchantOrderId) {
        return paymentRequestRepository.findByMerchantOrderId(merchantOrderId);
    }
}
