package com.sep.bitcoin;

import lombok.Data;

/**
 * Data requested to create order on coingate sandbox
 * */
@Data
public class Order {

    private Long order_id;
    private double price_amount;
    private Currency price_currency;
    private Currency receive_currency;
    private String title;
    private String description;
    private String callback_url;
    private String cancel_url;
    private String success_url;
    private String token;

    public Order() {}
    public Order(Long order_id, double price_amount, Currency price_currency, Currency receive_currency,
                 String title, String description, String callback_url, String cancel_url, String success_url, String token) {
        this.order_id = order_id;
        this.price_amount = price_amount;
        this.price_currency = price_currency;
        this.receive_currency = receive_currency;
        this.title = title;
        this.description = description;
        this.callback_url = callback_url;
        this.cancel_url = cancel_url;
        this.success_url = success_url;
        this.token = token;
    }
}
