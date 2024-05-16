package com.javaweb.repository;

import com.javaweb.model.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer> {
    Boolean existsByEmailAndPassword(String email, String password);
    Boolean existsByEmail(String email);
    CustomerEntity findByEmail(String email);
}
