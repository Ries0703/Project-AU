package com.javaweb.repository;

import com.javaweb.model.entity.DishEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishRepository extends JpaRepository<DishEntity, Integer> {
}
