package com.javaweb.model.dto;

import lombok.Data;

@Data
public class RestaurantLoginDto {
    private String email;
    private String password;
}