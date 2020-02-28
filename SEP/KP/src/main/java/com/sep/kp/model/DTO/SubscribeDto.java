package com.sep.kp.model.DTO;

import com.sep.kp.model.FrequencyPayment;
import com.sep.kp.model.PaymentTypePlan;
import lombok.Data;

@Data
public class SubscribeDto {
    private Double price;
    private String currency;
    private String nameOfJournal;
    private String description;
    private PaymentTypePlan typeOfPlan;
    private FrequencyPayment frequencyPayment;
    private int frequencyInterval;
    private int cycles;
}
