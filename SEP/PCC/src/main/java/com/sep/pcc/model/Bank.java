package com.sep.pcc.model;

import javax.persistence.*;
import javax.persistence.Table;

@Entity
@Table
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int bankNumber;

    @Column
    private String bankName;


    public Bank() {
    }

    public Bank(int bankNumber, String bankName) {
        this.bankNumber = bankNumber;
        this.bankName = bankName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(int bankNumber) {
        this.bankNumber = bankNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
