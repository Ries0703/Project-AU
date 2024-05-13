package com.javaweb.model.dto;

import lombok.Data;

@Data
public class RestaurantDto {
    private String name;
    private Double stars;
    private byte[] image;
    private Integer distance;
}
