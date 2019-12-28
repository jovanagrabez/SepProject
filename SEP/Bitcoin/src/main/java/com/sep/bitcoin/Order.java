package com.sep.bitcoin;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Data requested to create order on coingate sandbox
 * */
@Data
@Entity
@Table(name = "TRANSACTION")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long order_id;      // id bitcoin order-a
    @Column
    private String hashedOrderId;
    @Column
    private double price_amount;
    @Column
    private Currency price_currency;
    @Column
    private Currency receive_currency;
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private String callback_url;
    @Column
    private String cancel_url;
    @Column
    private String success_url;
    @Column
    private String token;
    @Column
    private String status;

    public Order() {}
    public Order(Long order_id, String hashedOrderId, double price_amount, Currency price_currency, Currency receive_currency,
                 String title, String description, String callback_url, String cancel_url, String success_url, String token, String status) {
        this.order_id = order_id;
        this.hashedOrderId = hashedOrderId;
        this.price_amount = price_amount;
        this.price_currency = price_currency;
        this.receive_currency = receive_currency;
        this.title = title;
        this.description = description;
        this.callback_url = callback_url;
        this.cancel_url = cancel_url;
        this.success_url = success_url;
        this.token = token;
        this.status = status;
    }
}
