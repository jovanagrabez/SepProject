package com.sep.nc.entity.dto;

import com.sep.nc.entity.User;
import com.sep.nc.entity.enumeration.PaymentType;
import com.sep.nc.entity.enumeration.ScientificArea;
import lombok.Data;

import java.util.List;

@Data
public class ShowMagazinesDto {

    private Long id;

    private String name;

    private String ISSNNumber;

    private List<ScientificArea> scientificAreas;

    private PaymentType paymentType;

    private double price;

    public ShowMagazinesDto(Long id, String name, String ISSNNumber, List<ScientificArea> scientificAreas, PaymentType paymentType, double price) {
        this.id = id;
        this.name = name;
        this.ISSNNumber = ISSNNumber;
        this.scientificAreas = scientificAreas;
        this.paymentType = paymentType;
        this.price = price;
    }
}
