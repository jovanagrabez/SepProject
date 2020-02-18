package com.sep.nc.entity.dto;

import com.sep.nc.entity.enumeration.PurchaseStatus;
import lombok.Data;

@Data
public class MagazinePurchaseDto {
    private ShowMagazinesDto magazine;
    private PurchaseStatus status;

    public MagazinePurchaseDto(ShowMagazinesDto magazine, PurchaseStatus status) {
        this.magazine = magazine;
        this.status = status;
    }
}
