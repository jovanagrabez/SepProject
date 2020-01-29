package com.sep.nc.entity.dto;

import com.sep.nc.entity.enumeration.TypeOfProduct;
import lombok.Data;

@Data
public class BuyProductDto {

    private Long productId;
    private String productName;
    private String userEmail;
    private double price;
    private TypeOfProduct typeOfProduct;
    private Long purchaseId;

    public BuyProductDto(Long productId, String productName, String userEmail, double price, TypeOfProduct typeOfProduct, Long purchaseId) {
        this.productId = productId;
        this.productName = productName;
        this.userEmail = userEmail;
        this.price = price;
        this.typeOfProduct = typeOfProduct;
        this.purchaseId = purchaseId;
    }
}
