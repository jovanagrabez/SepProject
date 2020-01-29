package com.sep.bitcoin;

import lombok.Data;

import java.util.Date;
@Data
public class CheckOrderStatusDto {

    private Long id;
    private String status;
    private Currency price_currency;
    private double price_amount;
    private Currency pay_currency;
    private double pay_amount;
    private Currency receive_currency;
    private double receive_amount;
    private Date created_at;
    private Date expire_at;
    private String payment_address;
    private Long order_id;
    private double underpaid_amount;
    private double overpaid_amount;
    private boolean is_refundable;
    private String payment_url;

}
