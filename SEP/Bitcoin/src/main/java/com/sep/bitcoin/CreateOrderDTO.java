package com.sep.bitcoin;

import lombok.Data;

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
    private String bitcoinToken;

}
