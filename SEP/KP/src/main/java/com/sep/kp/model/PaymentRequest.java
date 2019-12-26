package com.sep.kp.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class PaymentRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String merchantId;

    @Column
    private String merchantPassword;

    @Column
    private double amount;

    @Column
    private Long merchantOrderId;

    @Column
    private Date merchantTimestamp;

    @Column
    private String successUrl;

    @Column
    private String failedUrl;

    @Column
    private String errorUrl;

    public PaymentRequest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantPassword() {
        return merchantPassword;
    }

    public void setMerchantPassword(String merchantPassword) {
        this.merchantPassword = merchantPassword;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Long getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(Long merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }

    public Date getMerchantTimestamp() {
        return merchantTimestamp;
    }

    public void setMerchantTimestamp(Date merchantTimestamp) {
        this.merchantTimestamp = merchantTimestamp;
    }

    public String getSuccessUrl() {
        return successUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

    public String getFailedUrl() {
        return failedUrl;
    }

    public void setFailedUrl(String failedUrl) {
        this.failedUrl = failedUrl;
    }

    public String getErrorUrl() {
        return errorUrl;
    }

    public void setErrorUrl(String errorUrl) {
        this.errorUrl = errorUrl;
    }
}
