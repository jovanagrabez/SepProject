package com.sep.bank.model.DTO;

import java.util.Date;

public class CardAmountDTO {

    private Long merchantOrderId;
    private Long paymentId;
    private String pan;
    private int securityCode;
    private String cardHolderName;
    private Date validTo;
    private double amount;
    private String hashedId;
    private Long sellerId;
    private String merchantId;

    public CardAmountDTO() {
    }

    public CardAmountDTO(Long sellerId,Long merchantOrderId, Long paymentId, String pan, int securityCode, String cardHolderName, Date validTo, double amount) {
        this.merchantOrderId = merchantOrderId;
        this.paymentId = paymentId;
        this.pan = pan;
        this.securityCode = securityCode;
        this.cardHolderName = cardHolderName;
        this.validTo = validTo;
        this.amount = amount;
        this.sellerId = sellerId;
    }


    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
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

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public int getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(int securityCode) {
        this.securityCode = securityCode;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getHashedId() {
        return hashedId;
    }

    public void setHashedId(String hashedId) {
        this.hashedId = hashedId;
    }
}
