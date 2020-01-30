package com.sep.kp.model.DTO;

import lombok.Data;

@Data
public class SubscribeDto {
    private Long journalId;

    public SubscribeDto(Long journalId ){
        this.journalId = journalId;
    }
}
