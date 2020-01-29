package com.sep.paypal.model;

import com.sep.paypal.model.enumeration.FrequencyPayment;
import com.sep.paypal.model.enumeration.PaymentTypePlan;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Data
@Builder
@RequiredArgsConstructor
public class CreatePlanRequest {
    private Double price;
    private String currency;
    private String nameOfJournal;
    private String description;
    private PaymentTypePlan typeOfPlan;
    private FrequencyPayment frequencyPayment;
    private int frequencyInterval;
    private int cycles;
}
