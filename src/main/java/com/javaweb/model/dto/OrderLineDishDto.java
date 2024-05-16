package com.javaweb.model.dto;

import lombok.Data;


@Data
public class OrderLineDishDto {
    private String dishName;
    private Integer quantity = 1;
    private Double price = 0.0;
}
