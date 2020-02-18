package com.sep.kp.service;

import com.sep.kp.model.PaymentMethod;
import com.sep.kp.model.Seller;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SellerService {

    List<Seller> getAllMethods(String client);
    boolean newSellerPaymentMethods(List<PaymentMethod> paymentMethods, Long userId);
    Seller getSellerByHashedTransactionId(String hashedId);

}
