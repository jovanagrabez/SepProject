package com.sep.kp.service;

import com.sep.kp.model.PaymentRequest;
import org.springframework.stereotype.Service;

@Service
public interface PaymentRequestService {

    PaymentRequest createPaymentRequest(String clientID, double amount);
    PaymentRequest getPaymentRequest(Long merchantOrderId);
}
