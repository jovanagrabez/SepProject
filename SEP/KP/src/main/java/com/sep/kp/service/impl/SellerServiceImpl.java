package com.sep.kp.service.impl;


import com.sep.kp.model.Seller;
import com.sep.kp.model.Transaction;
import com.sep.kp.repository.SellerRepository;
import com.sep.kp.repository.TransactionRepository;
import com.sep.kp.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public List<Seller> getAllMethods(String client) {
        return sellerRepository.findByClient(client);
    }

    @Override
    public Seller getSellerByHashedTransactionId(String hashedId) {
        Transaction t = this.transactionRepository.findTransactionByIdHashValue(hashedId);
        Seller seller = this.sellerRepository.findSellerById(t.getSellerId());
        return seller;
    }
}
