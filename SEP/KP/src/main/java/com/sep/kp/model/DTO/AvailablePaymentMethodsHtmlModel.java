package com.sep.kp.model.DTO;

import lombok.Data;

@Data
public class AvailablePaymentMethodsHtmlModel {

    private boolean bitcoin;
    private boolean paypal;
    private boolean bank;
    private String bitcoinToken;
    // TODO Dodati potrebne podatke za ostale nacine placanja


}
