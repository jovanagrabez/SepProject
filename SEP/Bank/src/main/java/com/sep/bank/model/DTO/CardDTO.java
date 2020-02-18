package com.sep.bank.model.DTO;

import java.util.Date;

public class CardDTO {

    private String pan;
    private String securityCode;
    private String cardHolderName;
    private Date validTo;

    public CardDTO() {
    }

    public CardDTO(String pan, String securityCode, String cardHolderName, Date validTo) {
        this.pan = pan;
        this.securityCode = securityCode;
        this.cardHolderName = cardHolderName;
        this.validTo = validTo;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
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
}
