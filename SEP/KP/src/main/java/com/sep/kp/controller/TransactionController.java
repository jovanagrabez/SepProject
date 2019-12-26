package com.sep.kp.controller;

import com.sep.kp.model.DTO.TransactionResultCustomerDTO;
import com.sep.kp.model.DTO.TransactionResultDTO;
import com.sep.kp.model.Transaction;
import com.sep.kp.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;


    @PostMapping(value = "/finish-transaction")
    public ResponseEntity<TransactionResultCustomerDTO> finishTransaction(@RequestBody TransactionResultDTO transactionDTO) {
        Transaction transaction = new Transaction();
        transaction.setAcquirerOrderId(transactionDTO.getAcquirerOrderId());
        transaction.setAmount(transactionDTO.getAmount());

        transaction.setMerchantOrderId(transactionDTO.getMerchantOrderId());
        transaction.setPaymentId(transactionDTO.getPaymentId());
        transaction.setStatus(transactionDTO.getStatus());
        transaction.setTimestamp(transactionDTO.getAcquirerTimestamp());
        String resultUrl = transactionService.endTransaction(transaction);

        TransactionResultCustomerDTO transactionCustomer = new TransactionResultCustomerDTO(transaction.getMerchantOrderId(),
                transaction.getAcquirerOrderId(), transaction.getTimestamp(),
                transaction.getPaymentId(), resultUrl, transaction.getAmount(), transaction.getStatus());

        return ResponseEntity.ok().body(transactionCustomer);
    }

}
