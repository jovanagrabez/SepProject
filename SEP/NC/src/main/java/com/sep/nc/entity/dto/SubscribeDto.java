package com.sep.nc.entity.dto;

import com.sep.nc.entity.enumeration.FrequencyPayment;
import com.sep.nc.entity.enumeration.PaymentTypePlan;
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
