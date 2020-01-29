package com.sep.paypal.model;

import lombok.Data;

import javax.persistence.*;

@Table
@Entity
@Data
public class Transaction {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String hashedTransactionId;

    @Column
    private String paymentId;
}
