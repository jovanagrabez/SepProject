package com.sep.kp.model.DTO;

import lombok.Data;

import java.io.Serializable;

@Data
public class CreateSellerDto implements Serializable {
    private String userId;
    private String merchantId;
    private String bankName;
    private String clientId;
    private String clientSecret;
    private String bitcoinToken;
}
