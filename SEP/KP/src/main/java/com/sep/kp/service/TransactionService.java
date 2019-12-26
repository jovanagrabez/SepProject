package com.sep.kp.service;

import com.sep.kp.model.Transaction;
import org.springframework.stereotype.Service;

@Service
public interface TransactionService {
    String endTransaction(Transaction transaction);
}
