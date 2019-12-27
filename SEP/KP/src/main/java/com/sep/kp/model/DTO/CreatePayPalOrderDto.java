package com.sep.kp.model.DTO;

import com.sep.kp.model.PayPalPaymentIntent;
import com.sep.kp.model.PayPalPaymentMethod;
import lombok.Data;

@Data
public class CreatePayPalOrderDto {
    private Double price;
    private String currency;
    private String nameOfJournal;
    private PayPalPaymentIntent paymentIntent;
    private PayPalPaymentMethod paymentMethod;
    private String description;
    private String hashedMagazineId;

}
