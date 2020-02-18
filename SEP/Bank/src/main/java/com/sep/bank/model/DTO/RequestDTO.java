package com.sep.bank.model.DTO;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RequestDTO {

    private Long merchantOrderId;
    private Date merchantTimestamp;
//    private String merchantId;
//    private String merchantPassword;
    private double priceAmount;
    private String successUrl;
    private String failedUrl;
    private String errorUrl;
    private String hashedOrderId;
    private Map<String, String> params;
//    private String bankName;


    public RequestDTO() {
        params = new HashMap<>();
    }

    public RequestDTO(Long merchantOrderId, Date merchantTimestamp, double priceAmount, String successUrl,
                      String failedUrl, String errorUrl, String hashedOrderId, Map<String, String> params) {
        this.merchantOrderId = merchantOrderId;
        this.merchantTimestamp = merchantTimestamp;
        this.priceAmount = priceAmount;
        this.successUrl = successUrl;
        this.failedUrl = failedUrl;
        this.errorUrl = errorUrl;
        this.hashedOrderId = hashedOrderId;
        this.params = params;
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

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}
