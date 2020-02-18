package com.sep.kp.controller;

import com.sep.kp.model.FormData;
import com.sep.kp.model.PaymentMethod;
import com.sep.kp.repository.FormDataRepository;
import com.sep.kp.repository.PaymentMethodRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/payment-methods")
public class PaymentMethodsController {

    private final PaymentMethodRepository paymentMethodRepository;
    private final FormDataRepository formDataRepository;

    public PaymentMethodsController(PaymentMethodRepository paymentMethodRepository, FormDataRepository formDataRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
        this.formDataRepository = formDataRepository;
    }

    @GetMapping
    public List<PaymentMethod> getAvailablePaymentMethods() {
        return this.paymentMethodRepository.findAll();
    }

    @PostMapping
    public ResponseEntity createPaymentMethod(@RequestBody PaymentMethod paymentMethod) {
        for (FormData formData : paymentMethod.getRequiredFormData()) {
            this.formDataRepository.save(formData);
        }
        return new ResponseEntity<>(this.paymentMethodRepository.save(paymentMethod), HttpStatus.OK);
    }
}
