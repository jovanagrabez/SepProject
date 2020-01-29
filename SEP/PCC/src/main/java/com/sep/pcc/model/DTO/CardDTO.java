package com.sep.pcc.model.DTO;

import java.util.Date;

public class CardDTO {

    private String pan;
    private int securityCode;
    private String cardHolderName;
    private Date expirationDate;

    public CardDTO() {
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

    public void setCardHolderName(String hardHolderName) {
        this.cardHolderName = hardHolderName;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
