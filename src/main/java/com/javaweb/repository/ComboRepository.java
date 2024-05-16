package com.javaweb.repository;

import com.javaweb.model.entity.ComboEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComboRepository extends JpaRepository<ComboEntity, Integer> {
}
