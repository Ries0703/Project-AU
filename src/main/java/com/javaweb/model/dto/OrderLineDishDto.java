package com.javaweb.model.dto;

import com.javaweb.model.entity.ComboEntity;
import com.javaweb.model.entity.OrderEntity;
import lombok.Data;

import javax.persistence.*;

@Data
public class OrderLineDishDto {
    private String dishName;
    private Integer quantity = 1;
}
