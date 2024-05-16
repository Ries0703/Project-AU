package com.javaweb.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class RestaurantProfileDto {
    private RestaurantDto restaurantDto;
    private String address;
    private String phone;
    private List<DishDto> dishDtoList;
    private List<ComboDto> comboDtoList;
    private List<DishDto> bestsellerDishDtoList;
}
