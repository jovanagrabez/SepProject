package com.sep.bank.controller;


import com.sep.bank.model.DTO.CardAmountDTO;
import com.sep.bank.model.DTO.PaymentDTO;
import com.sep.bank.model.DTO.RequestDTO;
import com.sep.bank.model.DTO.TransactionDTO;
import com.sep.bank.model.Transaction;
import com.sep.bank.repository.TransactionRepository;
import com.sep.bank.service.BankService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin("https://localhost:5000")
public class AcquirerController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private BankService bankService;

    @Autowired
    private TransactionRepository transactionRepository;

    private ModelMapper modelMapper = new ModelMapper();
    @PostMapping("/get-payment-url")
    public ResponseEntity<PaymentDTO> getPaymentUrl(@RequestBody RequestDTO request) {

         System.out.println("ULAZIIIIIIIIIIII");
        PaymentDTO paymentData = bankService.getPaymentUrl(request);
        return ResponseEntity.ok(paymentData);
    }

    @GetMapping("/test")
    public ResponseEntity<String> testiraj(){
        String str = "LALALA";
        return ResponseEntity.ok(str);
    }

    @PostMapping("/pay-by-card")
    public Map<String, String> payByCard(@RequestBody CardAmountDTO cardAmountDTO) {
        Transaction transaction = bankService.checkBankForCard(cardAmountDTO);

        TransactionDTO transactionDTO = modelMapper.map(transaction, TransactionDTO.class);



        // final step - send transaction information to the payment concentrator
        String resp = restTemplate.postForObject("https://localhost:8762/koncentrator_placanja/api/transaction/finish-transaction", transactionDTO, String.class);

        Map<String, String> map = new HashMap<>();
        map.put("url", resp);
        map.put("status", transaction.getStatus());
        return map;
    }


    @GetMapping(value = "/status/{hashedId}")
    public ResponseEntity<String> getUpdateAboutOrderStatus(@PathVariable String hashedId) {
        Transaction transaction = transactionRepository.findTransactionByHashedOrderId(hashedId);

        if (transaction.getStatus().equals("SUCCESS")) {
            return ResponseEntity.ok("Paid");
        } else if (transaction.getStatus().equals("FAILED")) {
            return ResponseEntity.ok("Cancelled");
        } else {
            return ResponseEntity.ok("New");
        }
    }

}
