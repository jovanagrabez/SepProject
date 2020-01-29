package com.sep.pcc.model.DTO;


import java.util.Date;

public class AcquirerDataDTO {

    private Long acquirerOrderId;
    private Date acquirerTimestamp;
    private double amount;
    private CardDTO cardDTO;

    public AcquirerDataDTO() {
    }

    public AcquirerDataDTO(Long acquirerOrderId, Date acquirerTimestamp, CardDTO card, double amount) {
        this.acquirerOrderId = acquirerOrderId;
        this.acquirerTimestamp = acquirerTimestamp;
        this.cardDTO = card;
        this.amount = amount;
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

    public CardDTO getCard() {
        return cardDTO;
    }

    public void setCard(CardDTO card) {
        this.cardDTO = card;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
