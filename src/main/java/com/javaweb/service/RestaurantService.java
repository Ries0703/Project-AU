package com.javaweb.service;

import com.javaweb.model.dto.RestaurantDto;

import java.util.List;
import java.util.Map;

public interface RestaurantService {
    List<RestaurantDto> getRestaurantByParams(Map<String, Object> params, Integer customerPostcode);
}
