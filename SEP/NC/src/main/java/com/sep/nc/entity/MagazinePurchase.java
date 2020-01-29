package com.sep.nc.entity;

import com.sep.nc.entity.enumeration.PurchaseStatus;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table
public class MagazinePurchase {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Magazine magazine;

    @Enumerated
    private PurchaseStatus status;

    public MagazinePurchase() {}
    public MagazinePurchase(Magazine magazine, PurchaseStatus purchaseStatus) {
        this.magazine = magazine;
        this.status = purchaseStatus;
    }
}
