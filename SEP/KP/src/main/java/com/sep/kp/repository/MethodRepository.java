package com.sep.kp.repository;


import com.sep.kp.model.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MethodRepository extends JpaRepository<PaymentMethod, Long> {

    PaymentMethod findByName(String name);

}
