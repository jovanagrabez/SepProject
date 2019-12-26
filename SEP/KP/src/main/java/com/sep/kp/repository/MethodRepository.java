package com.sep.kp.repository;


import com.sep.kp.model.Method;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MethodRepository extends JpaRepository<Method, Long> {

    Method findByName(String name);

}
