package com.sep.nc.repository;

import com.sep.nc.entity.ScientificWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScientificWorkRepository extends JpaRepository<ScientificWork, Long> {
}
