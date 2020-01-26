package com.sep.paypal.service;

import com.paypal.api.payments.*;
import com.paypal.api.payments.Currency;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.sep.paypal.model.CreatePlanRequest;
import com.sep.paypal.model.JournalPlan;
import com.sep.paypal.model.enumeration.PaymentIntent;
import com.sep.paypal.model.enumeration.PaymentMethod;
import com.sep.paypal.repository.JournalPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.log4j.Log4j2;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Log4j2
@Service
public class PaypalService {
    @Autowired
    private APIContext apiContext;

    @Autowired
    private JournalPlanRepository journalPlanRepository;

    public Payment createPayment(
            Double total,
            String currency,
            PaymentMethod method,
            PaymentIntent intent,
            String description,
            String cancelUrl,
            String successUrl) throws PayPalRESTException{
        Amount amount = new Amount();
        amount.setCurrency(currency);
        amount.setTotal(String.valueOf(total));

        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(method.toString());
        Payment payment = new Payment();
        payment.setIntent(intent.toString());
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);

        return payment.create(apiContext);
    }

    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException{
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        return payment.execute(apiContext, paymentExecute);
    }

    public void createPlanForSubscription(CreatePlanRequest request){
        //Build plan object
        Plan plan = new Plan();
        plan.setName(request.getNameOfJournal());
        plan.setDescription(request.getDescription());
        plan.setType("INFINITE");

        //payment definitions
        PaymentDefinition paymentDefinition = new PaymentDefinition();
        paymentDefinition.setName(String.format("%s payments", request.getTypeOfPlan().name()));
        paymentDefinition.setType(request.getTypeOfPlan().name());
        paymentDefinition.setFrequency(request.getFrequencyPayment().name());
        paymentDefinition.setFrequencyInterval(String.valueOf(request.getFrequencyInterval()));
        paymentDefinition.setCycles(String.valueOf(request.getCycles()));

        //currency
        Currency currency = new Currency();
        currency.setCurrency(request.getCurrency());
        currency.setValue(String.valueOf(request.getPrice()));
        paymentDefinition.setAmount(currency);

        //payment definition
        List<PaymentDefinition> paymentDefinitionList = new ArrayList<>();
        paymentDefinitionList.add(paymentDefinition);
        plan.setPaymentDefinitions(paymentDefinitionList);

        //merchant preferences
        MerchantPreferences merchantPreferences = new MerchantPreferences();
        merchantPreferences.setSetupFee(currency);
        merchantPreferences.setCancelUrl("https://example.com/cancel");
        merchantPreferences.setReturnUrl("https://localhost:8762/paypal_service/api/plan/finishSubscription");
        merchantPreferences.setMaxFailAttempts("0");
        merchantPreferences.setAutoBillAmount("YES");
        merchantPreferences.setInitialFailAmountAction("CONTINUE");
        plan.setMerchantPreferences(merchantPreferences);

        activatePlan(plan, request.getNameOfJournal());
    }

    private void activatePlan(Plan plan, String nameOfJournal) {
        try {
            Plan createdPlan = plan.create(apiContext);
            log.info("Created plan with id = {}", createdPlan.getId());
            log.info("Plan state = {}", createdPlan.getState());
            // Set up plan activate PATCH request
            List<Patch> patchRequestList = new ArrayList<>();
            Map<String, String> value = new HashMap<>();
            value.put("state", "ACTIVE");

            // Create update object to activate plan
            Patch patch = new Patch();
            patch.setPath("/");
            patch.setValue(value);
            patch.setOp("replace");
            patchRequestList.add(patch);

            // Activate plan
            createdPlan.update(apiContext, patchRequestList);
            JournalPlan journalPlan;
            journalPlan = JournalPlan.builder().journal(nameOfJournal).planId(createdPlan.getId()).build();
            journalPlanRepository.save(journalPlan);

        } catch (PayPalRESTException e) {
            log.error(e.getDetails().getMessage());
        }
    }

    public URL subscribeToPlan(String nameOfJournal) {
        Agreement agreement = new Agreement();
        agreement.setName(String.format("Subscription for %s", nameOfJournal));
        agreement.setDescription("Basic Agreement");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

        // Add 30 seconds to make sure Paypal accept the agreement date
        Date rightNow = new Date(new Date().getTime() + 30000);
        agreement.setStartDate(df.format(rightNow));

        Plan plan = new Plan();
        plan.setId(journalPlanRepository.findJournalPlanByJournal(nameOfJournal).getPlanId());
        agreement.setPlan(plan);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");
        agreement.setPayer(payer);

        try {
            agreement = agreement.create(apiContext);

            for (Links links : agreement.getLinks()) {
                if ("approval_url".equals(links.getRel())) {
                    return new URL(links.getHref());
                    //REDIRECT USER TO url
                }
            }
        } catch (UnsupportedEncodingException | PayPalRESTException | MalformedURLException e) {
            log.error(e.getMessage());
        }
        return null;
    }


    public void finishSubscription(String token) {
        Agreement agreement = new Agreement();
        agreement.setToken(token);

        try {
            Agreement activeAgreement = agreement.execute(apiContext, agreement.getToken());
            log.info("Agreement created with ID {}", activeAgreement.getId());
        } catch (PayPalRESTException e) {
            log.error(e.getDetails().getMessage());
        }
    }

}
