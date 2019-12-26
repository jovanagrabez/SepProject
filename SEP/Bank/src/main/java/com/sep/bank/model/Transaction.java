package com.sep.bank.model;


import javax.persistence.*;
import java.util.Date;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long acquirerOrderId;

    @Column
    private Long merchantOrderId;

    @Column
    private Long paymentId;

    @Column
    private Date timestamp;

    @Column
    private double amount;

    @Column
    private String resultUrl;


    @Column
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Transaction(Long acquirerOrderId, Long merchantOrderId, Long paymentId, Date timestamp, double amount, String resultUrl, String status) {
        this.acquirerOrderId = acquirerOrderId;
        this.merchantOrderId = merchantOrderId;
        this.paymentId = paymentId;
        this.timestamp = timestamp;
        this.amount = amount;
        this.resultUrl = resultUrl;
        this.status = status;
    }

    public Transaction() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAcquirerOrderId() {
        return acquirerOrderId;
    }

    public void setAcquirerOrderId(Long acquirerOrderId) {
        this.acquirerOrderId = acquirerOrderId;
    }

    public Long getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(Long merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getResultUrl() {
        return resultUrl;
    }

    public void setResultUrl(String resultUrl) {
        this.resultUrl = resultUrl;
    }
}
