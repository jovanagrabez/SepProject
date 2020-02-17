package com.sep.kp.service;

import com.sep.kp.model.DTO.AvailablePaymentMethodsDto;
import com.sep.kp.model.DTO.CreateTransactionDto;
import com.sep.kp.model.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface TransactionService {
    String endTransaction(Transaction transaction);
    Transaction createTransaction(CreateTransactionDto createTransactionDto);
    List<AvailablePaymentMethodsDto> getAvailablePayments(String hashedId);
    Transaction getTransactionByMagazinePurchaseId(Long id);
}
