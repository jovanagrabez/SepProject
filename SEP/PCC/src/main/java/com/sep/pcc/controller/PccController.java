package com.sep.pcc.controller;


import com.sep.pcc.model.Bank;
import com.sep.pcc.model.DTO.AcquirerDataDTO;
import com.sep.pcc.model.DTO.CardDTO;
import com.sep.pcc.model.DTO.PaymentResultDTO;
import com.sep.pcc.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "/api")
public class PccController {

    @Autowired
    private BankService bankService;

    @Autowired
    private RestTemplate restTemplate;

    private static final String PORT = "8762/";
    private static final String HOST = "http://localhost:";
    private static final String PATH = "/pay-by-card-forwarded";

    @PostMapping("/forward-to-bank")
    public PaymentResultDTO forwardToBank(@RequestBody AcquirerDataDTO acquirerDataDTO) {
        CardDTO cardDTO = acquirerDataDTO.getCard();

        Bank bank = bankService.findBank(cardDTO.getPan());

        String bankUrl = HOST + PORT + bank.getBankName() + PATH;

        // vraca banci prodavca acqOrderId acqTimestamp, issuerOrderId, issuerTimestap i status transakcije
        return restTemplate.postForObject("https://localhost:".concat(bank.getBankName()+"/api/issuer/pay-by-card-forwarded"), acquirerDataDTO, PaymentResultDTO.class);
    }
}
