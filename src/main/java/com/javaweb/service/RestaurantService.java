package com.javaweb.service;

import com.javaweb.model.dto.RestaurantDto;

import java.util.List;
import java.util.Map;

public interface RestaurantService {
    List<RestaurantDto> getRestaurantByName(String name, Integer customerPostcode);
    List<RestaurantDto> getAllRestaurants(Integer customerPostcode);
    List<RestaurantDto> getRestaurantByParams(Map<String, Object> params, Integer customerPostcode);
}
