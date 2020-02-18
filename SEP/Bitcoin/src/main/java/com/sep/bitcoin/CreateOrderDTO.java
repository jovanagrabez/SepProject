package com.sep.bitcoin;

import lombok.Data;

import java.util.Map;

@Data
public class CreateOrderDTO {

    private Long orderId;
    private String hashedOrderId;
    private double priceAmount;
    private Currency priceCurrency;
    private Currency receiveCurrency;
    private String title;
    private String description;
    private String callbackUrl;
    private String cancelUrl;
    private String successUrl;
    private String token;
    private Map<String, String> params;

}
