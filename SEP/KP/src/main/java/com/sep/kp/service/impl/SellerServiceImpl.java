package com.sep.kp.service.impl;


import com.sep.kp.model.DTO.CreateSellerDto;
import com.sep.kp.model.PaymentMethod;
import com.sep.kp.model.Seller;
import com.sep.kp.repository.PaymentMethodRepository;
import com.sep.kp.repository.SellerRepository;
import com.sep.kp.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Override
    public List<Seller> getAllMethods(String client) {
        return sellerRepository.findByClient(client);
    }

    @Override
    public boolean newSellerPaymentMethods(CreateSellerDto createSellerDto) {

        Seller seller = this.sellerRepository.findSellerByUserId(Long.parseLong(createSellerDto.getUserId()));
        if (seller == null) {
            seller = new Seller();
            seller.setUserId(Long.parseLong(createSellerDto.getUserId()));
        }

        if (seller.getPaymentMethods() == null) {
            seller.setPaymentMethods(new ArrayList<>());
        }

        if (!createSellerDto.getBankName().isEmpty() && !createSellerDto.getMerchantId().isEmpty()) {
            PaymentMethod paymentMethod = paymentMethodRepository.findByName("Bank");
            seller.getPaymentMethods().add(paymentMethod);
        }
        if (!createSellerDto.getClientId().isEmpty() && !createSellerDto.getClientSecret().isEmpty()) {
            PaymentMethod paymentMethod = paymentMethodRepository.findByName("PayPal");
            seller.getPaymentMethods().add(paymentMethod);
        }
        if (!createSellerDto.getBitcoinToken().isEmpty()) {
            PaymentMethod paymentMethod = paymentMethodRepository.findByName("Bitcoin");
            seller.getPaymentMethods().add(paymentMethod);
            seller.setBitcoinToken(createSellerDto.getBitcoinToken());
        }
        this.sellerRepository.save(seller);

        return true;
    }
}
