package com.sep.bank.controller;


import com.sep.bank.model.DTO.*;
import com.sep.bank.model.Transaction;
import com.sep.bank.service.BankService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "/api")
public class AcquirerController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private BankService bankService;

    private ModelMapper modelMapper = new ModelMapper();
    @PostMapping("/get-payment-url")
    public ResponseEntity<PaymentDTO> getPaymentUrl(@RequestBody RequestDTO request) {

        System.out.println("KKKKKKKKKKKKKKK"+ request.getAmount());

        PaymentDTO paymentData = bankService.getPaymentUrl(request);
        return ResponseEntity.ok(paymentData);
    }

    @PostMapping("/pay-by-card")
    public void payByCard(@RequestBody CardAmountDTO cardAmountDTO) {
        Transaction transaction = bankService.checkBankForCard(cardAmountDTO);

        TransactionDTO transactionDTO = modelMapper.map(transaction, TransactionDTO.class);

        System.out.println(transactionDTO.getAmount());
        System.out.println(transactionDTO.getMerchantOrderId());
        System.out.println(transactionDTO.getPaymentId());



 /*       TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAcquirerOrderId(transaction.getAcquirerOrderId());
        transactionDTO.setAcquirerTimestamp(transaction.getTimestamp());
        transactionDTO.setAmount(transaction.getAmount());
        transactionDTO.setMerchantOrderId(transaction.getMerchantOrderId());
        transactionDTO.setPaymentId(transaction.getPaymentId());
        transactionDTO.setResultUrl(transaction.getResultUrl());
        transactionDTO.setStatus(transaction.getStatus());
        transactionDTO.setId(transaction.getId());
*/
        // final step - send transaction information to the payment concentrator
        restTemplate.postForObject("https://localhost:8762/koncentrator_placanja/api/transaction/finish-transaction", transactionDTO, TransactionDTO.class);

    }

}
