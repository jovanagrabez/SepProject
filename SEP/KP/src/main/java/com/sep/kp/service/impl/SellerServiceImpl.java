package com.sep.kp.service.impl;


import com.sep.kp.model.*;
import com.sep.kp.repository.PaymentDataRepository;
import com.sep.kp.repository.PaymentMethodRepository;
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
    @Autowired
    private PaymentDataRepository paymentDataRepository;

    @Override
    public List<Seller> getAllMethods(String client) {
        return sellerRepository.findByClientId(Long.parseLong(client));
    }

    @Override
    public boolean newSellerPaymentMethods(List<PaymentMethod> paymentMethods, Long userId) {

        Seller seller = this.sellerRepository.findSellerByUserId(userId);
        if (seller == null) {
            seller = new Seller();
            seller.setClientId(userId);
        }

        if (seller.getPaymentMethods() == null) {
            seller.setPaymentMethods(new ArrayList<>());
        }
        if (seller.getPaymentsData() == null) {
            seller.setPaymentsData(new ArrayList<>());
        }

        for (PaymentMethod paymentMethod : paymentMethods) {
            List<PaymentData> paymentsData = new ArrayList<>();
            boolean valid = true;
            for (FormData formData : paymentMethod.getRequiredFormData()) {
                if (formData.getValue() != null && !formData.getValue().equals("")) {
                    paymentsData.add(new PaymentData(formData.getCode(), formData.getValue()));
                } else {
                    valid = false;
                }
            }
            if (paymentsData.size() != 0 && valid == true) {
                seller.getPaymentMethods().add(paymentMethod);
                seller.getPaymentsData().addAll(paymentsData);      // doda sva polja potrebna za banku npr
            }
        }

        List<PaymentData> paymentData = seller.getPaymentsData();
        seller.setPaymentsData(null);
        this.sellerRepository.save(seller);

        for (int i = 0; i < paymentData.size(); i++) {
            PaymentData pd = new PaymentData();
            pd.setName(paymentData.get(i).getName());
            pd.setValue(paymentData.get(i).getValue());
            paymentData.set(i, this.paymentDataRepository.save(pd));
        }

        seller.setPaymentsData(paymentData);
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
