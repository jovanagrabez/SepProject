package com.sep.bank.controller;


import com.sep.bank.model.DTO.CardAmountDTO;
import com.sep.bank.model.DTO.PaymentDTO;
import com.sep.bank.model.DTO.RequestDTO;
import com.sep.bank.model.Transaction;
import com.sep.bank.service.BankService;
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

    @PostMapping("/get-payment-url")
    public ResponseEntity<PaymentDTO> getPaymentUrl(@RequestBody RequestDTO request) {
        PaymentDTO paymentData = bankService.getPaymentUrl(request);
        return ResponseEntity.ok(paymentData);
    }

    @PostMapping("/pay-by-card")
    public void payByCard(@RequestBody CardAmountDTO cardAmountDTO) {
        Transaction transaction = bankService.checkBankForCard(cardAmountDTO);

    //    TransactionDTO transactionDTO = modelMapper.map(transaction, TransactionDTO.class);

        // final step - send transaction information to the payment concentrator
        restTemplate.postForObject("https://localhost:8762/koncentrator_placanja/finish-transaction", transaction, Transaction.class);

    }

}
