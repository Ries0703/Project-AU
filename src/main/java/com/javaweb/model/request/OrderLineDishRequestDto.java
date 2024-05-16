package com.javaweb.model.request;

import lombok.Data;

@Data
public class OrderLineDishRequestDto {
    private Integer dishId;
    private Integer quantity;
}