package com.sep.kp.model.DTO;

import com.sep.kp.model.Currency;
import com.sep.kp.model.PayPalPaymentIntent;
import com.sep.kp.model.PayPalPaymentMethod;
import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class CreateOrderDto {

    private Long orderId;
    private String hashedOrderId;
    private double priceAmount;
    private Currency priceCurrency;
    private Currency receiveCurrency;
    private String title;
    private String description;

    private Map<String, String> params;     // params specific for each payment service
//
//    private String bitcoinToken;
//
//    private PayPalPaymentIntent paymentIntent;
//    private PayPalPaymentMethod paymentMethod;
//
//    private Long merchantId;
//    private String merchantPassword;
//    private Long merchantOrderId;
//    private Date merchantTimestamp;
//    private String bankName;
}
