package com.sep.nc.repository;

import com.sep.nc.entity.MagazinePurchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MagazinePurchasesRepository extends JpaRepository<MagazinePurchase, Long> {

    @Query(value = "select * from magazine_purchase " +
            "left join user_magazine_purchases ump on magazine_purchase.id = ump.magazine_purchases_id " +
            "left join user u on ump.user_id = u.id where u.email = :userEmail", nativeQuery = true)
    List<MagazinePurchase> findMagazinePurchaseByUser(@Param(value = "userEmail") String userEmail);
}
