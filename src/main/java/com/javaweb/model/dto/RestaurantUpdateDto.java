package com.javaweb.model.dto;

import lombok.Data;

import java.time.LocalTime;

@Data
public class RestaurantUpdateDto {
    private Integer id;
    private String name;
    private String address;
    private Integer postCode;
    private LocalTime startTime;
    private LocalTime endTime;
    private String phone;
    private String category;
}
