package com.javaweb.model.dto;

import lombok.Data;

@Data
public class DishDto {
    private Integer id;
    private Integer restaurantId;
    private String name;
    private String description;
    private Double price;
    private byte[] image;
}
