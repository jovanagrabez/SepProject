package com.sep.paypal.controller;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.sep.paypal.model.PaymentRequest;
import com.sep.paypal.service.PaypalService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Log4j2
@Controller
public class PaypalController {

    private final PaypalService paypalService;

    private static final String SUCCESS_URL = "pay/success";
    private static final String CANCEL_URL = "pay/cancel";

    @GetMapping("/")
    public String home(){
        return "home";
    }


    @Autowired
    public PaypalController(PaypalService paypalService) {
        this.paypalService = paypalService;
    }

    @PostMapping(value = "pay")
    public String pay(@RequestBody PaymentRequest request) {
        String cancelUrl = "";
        String successUrl = "";
        successUrl = "http://localhost:8762/paypal/" + SUCCESS_URL;
        cancelUrl = "http://localhost:8762/paypal/" + CANCEL_URL;
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
            System.out.println(payment.toJSON());
            if (payment.getState().equals("approved")) {
                return "success";
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return "redirect:/";
    }
}
