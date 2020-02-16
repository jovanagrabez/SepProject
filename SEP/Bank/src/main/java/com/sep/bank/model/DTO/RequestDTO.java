package com.sep.bank.model.DTO;

import java.util.Date;

public class RequestDTO {

    private Long merchantOrderId;
    private Date merchantTimestamp;
    private String merchantId;
    private String merchantPassword;
    private double priceAmount;
    private String successUrl;
    private String failedUrl;
    private String errorUrl;
    private String hashedOrderId;
    private String bankName;


    public RequestDTO() {
    }

    public RequestDTO(Long merchantOrderId, Date merchantTimestamp, String merchantId, String merchantPassword,
                      double priceAmount, String successUrl, String failedUrl, String errorUrl, String hashedOrderId, String bankName) {
        this.merchantOrderId = merchantOrderId;
        this.merchantTimestamp = merchantTimestamp;
        this.merchantId = merchantId;
        this.merchantPassword = merchantPassword;
        this.priceAmount = priceAmount;
        this.successUrl = successUrl;
        this.failedUrl = failedUrl;
        this.errorUrl = errorUrl;
        this.hashedOrderId = hashedOrderId;
        this.bankName = bankName;
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

    public double getPriceAmount() {
        return priceAmount;
    }

    public void setPriceAmount(double priceAmount) {
        this.priceAmount = priceAmount;
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

    public String getHashedOrderId() {
        return hashedOrderId;
    }

    public void setHashedOrderId(String hashedOrderId) {
        this.hashedOrderId = hashedOrderId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
