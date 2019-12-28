package com.sep.kp.model.DTO;

import java.util.Date;

public class TransactionResultDTO {
    private Long merchantOrderId;
    private Long paymentId;
    private String status;
    private Long acquirerOrderId;
    private Date acquirerTimestamp;
    private double amount;
    private String hashedOrderId;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getAcquirerOrderId() {
        return acquirerOrderId;
    }

    public void setAcquirerOrderId(Long acquirerOrderId) {
        this.acquirerOrderId = acquirerOrderId;
    }

    public Date getAcquirerTimestamp() {
        return acquirerTimestamp;
    }

    public void setAcquirerTimestamp(Date acquirerTimestamp) {
        this.acquirerTimestamp = acquirerTimestamp;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getHashedOrderId() {
        return hashedOrderId;
    }

    public void setHashedOrderId(String hashedOrderId) {
        this.hashedOrderId = hashedOrderId;
    }
}
