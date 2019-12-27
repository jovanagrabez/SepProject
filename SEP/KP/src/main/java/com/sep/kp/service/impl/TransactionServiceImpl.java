package com.sep.kp.service.impl;


import com.sep.kp.model.PaymentRequest;
import com.sep.kp.model.Transaction;
import com.sep.kp.repository.TransactionRepository;
import com.sep.kp.service.PaymentRequestService;
import com.sep.kp.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private PaymentRequestService paymentRequestService;

    @Override
    public String endTransaction(Transaction transaction) {

     //   transaction.setStatus("SUCCESS");
        transactionRepository.save(transaction);
        PaymentRequest paymentRequest = paymentRequestService.getPaymentRequest(transaction.getMerchantOrderId());
        String status = transaction.getStatus();
        String url;
        switch (status) {
            case "SUCCESS":
                url = paymentRequest.getSuccessUrl();
                break;
            case "FAILED":
                url = paymentRequest.getFailedUrl();
                break;
            default:
                url = paymentRequest.getErrorUrl();
                break;
        }
        return url;
    }
}
