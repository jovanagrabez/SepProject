package com.sep.nc.entity.dto;

import lombok.Data;

@Data
public class SubscribeDto {
    private Long journalId;

    public SubscribeDto(Long journalId ){
        this.journalId = journalId;
    }
}
