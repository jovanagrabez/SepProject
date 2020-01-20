package com.sep.nc.entity.dto;

import com.sep.nc.entity.enumeration.PurchaseStatus;
import lombok.Data;

@Data
public class FinishedMagazineOrderDto {

    private String email;
    private PurchaseStatus purchaseStatus;
    private Long purchaseId;
}
