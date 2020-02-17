package com.sep.kp.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AvailablePaymentMethodsDto {

    private Long id;
    private String name;
    private String imageAssociated;
    private String url;


}
