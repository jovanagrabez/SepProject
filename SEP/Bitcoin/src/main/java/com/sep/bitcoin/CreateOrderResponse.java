package com.sep.bitcoin;

import lombok.Data;

@Data
public class CreateOrderResponse {

    private String id;
    private String status;
    private Currency price_currency;
    private double price_amount;
    private Currency receive_currency;
    private double receive_amount;
    private String created_at;
    private String order_id;
    private String payment_url;
    private String token;
}
