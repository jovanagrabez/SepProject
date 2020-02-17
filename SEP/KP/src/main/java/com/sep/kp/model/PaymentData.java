package com.sep.kp.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Table
@Entity
@Data
public class PaymentData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String value;

    public PaymentData() {}
    public PaymentData(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
