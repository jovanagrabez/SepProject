package com.sep.nc.entity.dto;

import lombok.Data;

@Data
public class BuyMagazineDto {

    private Long magazineId;
    private String magazineName;
    private Long userId;
    private String userEmail;

    public BuyMagazineDto(Long magazineId, String magazineName, Long userId, String userEmail) {
        this.magazineId = magazineId;
        this.magazineName = magazineName;
        this.userId = userId;
        this.userEmail = userEmail;
    }
}
