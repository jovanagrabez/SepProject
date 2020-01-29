package com.sep.kp.model.DTO;

import com.sep.kp.model.PurchaseStatus;
import lombok.Data;

@Data
public class FinishedMagazineOrderDto {

    private String email;
    private PurchaseStatus purchaseStatus;
    private Long purchaseId;

}
