package com.sep.paypal.model;

import com.sep.paypal.model.enumeration.PaymentIntent;
import com.sep.paypal.model.enumeration.PaymentMethod;
import lombok.*;

@Data
@AllArgsConstructor
@Builder
@RequiredArgsConstructor
public class PaymentRequest {

    private Double priceAmount;
    private String priceCurrency;
    private PaymentIntent paymentIntent;
    private PaymentMethod paymentMethod;
    private String description;
    private String hashedOrderId;

}
