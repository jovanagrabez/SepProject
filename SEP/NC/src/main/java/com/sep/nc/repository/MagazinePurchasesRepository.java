package com.sep.nc.repository;

import com.sep.nc.entity.MagazinePurchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MagazinePurchasesRepository extends JpaRepository<MagazinePurchase, Long> {
}
