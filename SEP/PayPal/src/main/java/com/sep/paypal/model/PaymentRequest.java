package com.sep.paypal.model;

import com.sep.paypal.model.enumeration.PaymentIntent;
import com.sep.paypal.model.enumeration.PaymentMethod;
import lombok.*;

@Data
@AllArgsConstructor
@Builder
@RequiredArgsConstructor
public class PaymentRequest {

    private Double price;
    private String currency;
    private String nameOfJournal;
    private PaymentIntent paymentIntent;
    private PaymentMethod paymentMethod;
    private String description;

}
