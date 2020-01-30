package com.sep.kp.service.impl;


import com.sep.kp.model.DTO.CreateSellerDto;
import com.sep.kp.model.PaymentMethod;
import com.sep.kp.model.Seller;
import com.sep.kp.repository.PaymentMethodRepository;
import com.sep.kp.model.Transaction;
import com.sep.kp.repository.SellerRepository;
import com.sep.kp.repository.TransactionRepository;
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

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public List<Seller> getAllMethods(String client) {
        return sellerRepository.findByClientId(Long.parseLong(client));
    }

    @Override
    public boolean newSellerPaymentMethods(CreateSellerDto createSellerDto) {

        Seller seller = this.sellerRepository.findSellerByUserId(Long.parseLong(createSellerDto.getUserId()));
        if (seller == null) {
            seller = new Seller();
            seller.setClientId(Long.parseLong(createSellerDto.getUserId()));
        }

        if (seller.getPaymentMethods() == null) {
            seller.setPaymentMethods(new ArrayList<>());
        }

        if (!createSellerDto.getBankName().isEmpty() && !createSellerDto.getMerchantId().isEmpty()) {
            PaymentMethod paymentMethod = paymentMethodRepository.findByName("Bank");
            if (!seller.getPaymentMethods().contains(paymentMethod)) {
                seller.getPaymentMethods().add(paymentMethod);
            }
            seller.setBankName(createSellerDto.getBankName());
            seller.setMerchantId(createSellerDto.getMerchantId());
        }
        if (!createSellerDto.getClientId().isEmpty() && !createSellerDto.getClientSecret().isEmpty()) {
            PaymentMethod paymentMethod = paymentMethodRepository.findByName("PayPal");
            if (!seller.getPaymentMethods().contains(paymentMethod)) {
                seller.getPaymentMethods().add(paymentMethod);
            }
            seller.setPaypalClientId(createSellerDto.getClientId());
            seller.setPaypalClientSecret(createSellerDto.getClientSecret());
        }
        if (!createSellerDto.getBitcoinToken().isEmpty()) {
            PaymentMethod paymentMethod = paymentMethodRepository.findByName("Bitcoin");
            if (!seller.getPaymentMethods().contains(paymentMethod)) {
                seller.getPaymentMethods().add(paymentMethod);
            }
            seller.setBitcoinToken(createSellerDto.getBitcoinToken());
        }
        this.sellerRepository.save(seller);

        return true;
    }

    @Override
    public Seller getSellerByHashedTransactionId(String hashedId) {
        Transaction t = this.transactionRepository.findTransactionByIdHashValue(hashedId);
        Seller seller = this.sellerRepository.findSellerById(t.getSellerId());
        return seller;
    }
}
