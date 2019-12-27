package com.sep.kp.model.DTO;

import com.sep.kp.model.Currency;
import lombok.Data;

@Data
public class CreateBitcoinOrderDTO {
    private Long orderId;
    private double priceAmount;
    private Currency priceCurrency;
    private Currency receiveCurrency;
    private String title;
    private String description;
    private String callbackUrl;
    private String cancelUrl;
    private String successUrl;
    private String token;
    private String bitcoinToken;

    public CreateBitcoinOrderDTO(Long orderId, double priceAmount, Currency priceCurrency, Currency receiveCurrency,
                                 String title, String description, String callbackUrl, String cancelUrl,
                                 String successUrl, String token, String bitcoinToken) {
        this.orderId = orderId;
        this.priceAmount = priceAmount;
        this.priceCurrency = priceCurrency;
        this.receiveCurrency = receiveCurrency;
        this.title = title;
        this.description = description;
        this.callbackUrl = callbackUrl;
        this.cancelUrl = cancelUrl;
        this.successUrl = successUrl;
        this.token = token;
        this.bitcoinToken = bitcoinToken;
    }
}
