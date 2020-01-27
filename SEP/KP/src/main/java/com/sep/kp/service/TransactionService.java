package com.sep.kp.service;

import com.sep.kp.model.DTO.AvailablePaymentMethodsHtmlModel;
import com.sep.kp.model.DTO.CreateTransactionDto;
import com.sep.kp.model.PaymentMethod;
import com.sep.kp.model.Transaction;
import com.sep.kp.model.TransactionStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface TransactionService {
    String endTransaction(Transaction transaction);
    Transaction createTransaction(CreateTransactionDto createTransactionDto);
    Map<String, String> generateHtmlForAvailablePayments(String hashedId);
    Transaction getTransactionByMagazinePurchaseId(Long id);
}
