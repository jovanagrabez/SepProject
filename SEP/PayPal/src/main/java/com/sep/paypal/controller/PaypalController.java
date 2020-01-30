package com.sep.paypal.controller;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.sep.paypal.TransactionRepository;
import com.sep.paypal.model.CreatePlanRequest;
import com.sep.paypal.model.PaymentRequest;
import com.sep.paypal.model.SubscribeDto;
import com.sep.paypal.model.Transaction;
import com.sep.paypal.service.PaypalService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URL;

@Log4j2
@RestController
@RequestMapping(value = "/api")
public class PaypalController {

    @Autowired
    private PaypalService paypalService;

    @Autowired
    private TransactionRepository transactionRepository;

    private static final String SUCCESS_URL = "/pay/success";
    private static final String CANCEL_URL = "/pay/cancel";

    @PostMapping(value = "/pay")
    public String pay(@RequestBody PaymentRequest request) {
        String cancelUrl = "";
        String successUrl = "";
        successUrl = "https://localhost:8762/paypal_service/api" + SUCCESS_URL;
        cancelUrl = "https://localhost:8762/paypal_service/api" + CANCEL_URL;
        try {
            Payment payment = paypalService.createPayment(
                    request.getPrice(),
                    request.getCurrency(),
                    request.getPaymentMethod(),
                    request.getPaymentIntent(),
                    request.getDescription(),
                    cancelUrl,
                    successUrl);
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

    @GetMapping(value = CANCEL_URL)
    public String cancelPay() {
        return "cancel";
    }

    @GetMapping(value = SUCCESS_URL)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            //System.out.println(payment.toJSON());
            if (payment.getState().equals("approved")) {
                return "success";
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return  "redirect:/";
    }


    @PostMapping(value = "/plan/createPlan")
    public ResponseEntity createPlanForSubscription(@RequestBody CreatePlanRequest requestCreatePlan) {
        paypalService.createPlanForSubscription(requestCreatePlan);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/plan/subscribe")
    public ResponseEntity subscribeToPlan(@RequestBody SubscribeDto subscribeDto) {
        String url = paypalService.subscribeToPlan(subscribeDto.getJournalId());
        return ResponseEntity.ok(url);
    }

    @GetMapping(value = "/plan/finishSubscription")
    public ResponseEntity finishSubscription(@RequestParam("token") String token){
        paypalService.finishSubscription(token);
        return ResponseEntity.ok("Subscription finished");
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
