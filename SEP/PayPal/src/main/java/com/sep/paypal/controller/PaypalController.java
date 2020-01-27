package com.sep.paypal.controller;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.sep.paypal.TransactionRepository;
import com.sep.paypal.model.PaymentRequest;
import com.sep.paypal.model.Transaction;
import com.sep.paypal.service.PaypalService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Arrays;

@Log4j2
@RestController
@RequestMapping(value = "/api")
public class PaypalController {

    @Autowired
    private PaypalService paypalService;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TransactionRepository transactionRepository;

    private static final String SUCCESS_URL = "/pay/success";
    private static final String CANCEL_URL = "/pay/cancel";
    private static final String SUCCESS_URL_REDIRECT = "https://localhost:8762/koncentrator_placanja/api/transaction/success/";
    private static final String CANCEL_URL_REDIRECT = "https://localhost:8762/koncentrator_placanja/api/transaction/cancel/";

    @PostMapping(value = "/pay")
    public String pay(@RequestBody PaymentRequest request) {
        String cancelUrl = "";
        String successUrl = "";
        successUrl = "https://localhost:8762/paypal_service/api" + SUCCESS_URL+"/"+request.getHashedMagazineId();
        cancelUrl = "https://localhost:8762/paypal_service/api" + CANCEL_URL+"/"+request.getHashedMagazineId();
        try {
            Payment payment = paypalService.createPayment(
                    request.getPrice(),
                    request.getCurrency(),
                    request.getPaymentMethod(),
                    request.getPaymentIntent(),
                    request.getDescription(),
                    cancelUrl,
                    successUrl);

            Transaction transactionForDatabase = new Transaction();
            transactionForDatabase.setPaymentId(payment.getId());
            transactionForDatabase.setHashedTransactionId(request.getHashedMagazineId());
            transactionRepository.save(transactionForDatabase);

            for (Links links : payment.getLinks()) {
                if (links.getRel().equals("approval_url")) {
                    return links.getHref();
                }
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return "redirect:/";
    }

    @GetMapping(value = CANCEL_URL+"/{hashedId}")
    public RedirectView cancelPay(@PathVariable String hashedId) {

//        hashedId = hashedId.substring(0, hashedId.length()-2);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        ResponseEntity<String> resp = restTemplate.getForEntity(CANCEL_URL_REDIRECT+hashedId, String.class);
        return new RedirectView("https://localhost:4200");

    }

    @GetMapping(value = SUCCESS_URL+"/{hashedId}")
    public RedirectView successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, @PathVariable String hashedId) {
        try {
//            hashedId = hashedId.substring(0, hashedId.length()-2);
            Payment payment = paypalService.executePayment(paymentId, payerId);

            //System.out.println(payment.toJSON());
            if (payment.getState().equals("approved")) {
                HttpHeaders requestHeaders = new HttpHeaders();
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

                ResponseEntity<String> resp = restTemplate.getForEntity(SUCCESS_URL_REDIRECT+hashedId, String.class);
                return new RedirectView("https://localhost:4200");
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return new RedirectView("https://localhost:4200");

    }

    @GetMapping(value = "/status/{hashedId}")
    public ResponseEntity getUpdatedTransactionStatus(@PathVariable String hashedId) throws PayPalRESTException {
        Transaction transaction = transactionRepository.findTransactionByHashedTransactionId(hashedId);
        Payment payment = paypalService.getPayment(transaction.getPaymentId());

        switch (payment.getState()){
//            case "CREATED":
            case "APPROVED": return ResponseEntity.ok("Paid");
            case "FAILED": return ResponseEntity.ok("Cancelled");
            default: return ResponseEntity.ok("New");
        }
    }


}
