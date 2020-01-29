package com.sep.bank.model.DTO;

public class PaymentDTO {

    private String paymentId;
    private double amount;
    private String paymentUrl;
    private Long merchantOrderId;


    public PaymentDTO() {
    }

    public PaymentDTO(String paymentId, double amount, String paymentUrl, Long merchantOrderId) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.paymentUrl = paymentUrl;
        this.merchantOrderId = merchantOrderId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentUrl() {
        return paymentUrl;
    }

    public void setPaymentUrl(String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }

    public Long getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(Long merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }
}
