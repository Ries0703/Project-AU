package com.javaweb.service;

import com.javaweb.model.dto.RestaurantDto;

import java.util.List;

public interface RestaurantService {
    List<RestaurantDto> getRestaurantByName(String name, Integer customerPostcode);
    List<RestaurantDto> getAllRestaurants(Integer customerPostcode);
}
