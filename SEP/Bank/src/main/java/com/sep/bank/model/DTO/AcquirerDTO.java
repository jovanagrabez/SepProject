package com.sep.bank.model.DTO;

import java.util.Date;

public class AcquirerDTO {

    private Long acquirerOrderId;
    private Date acquirerTimestamp;
    private double amount;
    private CardDTO card;


    public AcquirerDTO() {
    }

    public AcquirerDTO(Long acquirerOrderId, Date acquirerTimestamp, double amount, CardDTO card) {
        this.acquirerOrderId = acquirerOrderId;
        this.acquirerTimestamp = acquirerTimestamp;
        this.amount = amount;
        this.card = card;
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

    public CardDTO getCard() {
        return card;
    }

    public void setCard(CardDTO card) {
        this.card = card;
    }
}
