package com.sep.bank.controller;


import com.sep.bank.model.DTO.AcquirerDTO;
import com.sep.bank.model.DTO.PaymentResultDTO;
import com.sep.bank.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Random;

@RestController
public class IssuerController {

    @Autowired
    private BankService bankService;

    @PostMapping("/pay-by-card-forwarded")
    public PaymentResultDTO payByCardForwarded(@RequestBody AcquirerDTO
                                                           acquirerDataDTO) {
        String status = bankService.checkCard(acquirerDataDTO);

        PaymentResultDTO paymentResultDTO = new PaymentResultDTO();
        paymentResultDTO.setAcquirerOrderId(acquirerDataDTO.getAcquirerOrderId());
        paymentResultDTO.setAcquirerTimestamp(acquirerDataDTO.getAcquirerTimestamp());
        Random random = new Random();
        paymentResultDTO.setIssuerOrderId(random.nextLong());
        paymentResultDTO.setIssuerTimestamp(new Date());
        paymentResultDTO.setStatus(status);
        return paymentResultDTO;
    }

}
