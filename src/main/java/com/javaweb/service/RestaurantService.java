package com.javaweb.service;

import com.javaweb.model.dto.*;

import java.util.List;
import java.util.Map;

public interface RestaurantService {
    List<RestaurantDto> getRestaurantByParams(Map<String, Object> params, Integer customerPostcode);
    boolean existRestaurant(String email, String password);
    boolean existEmail(String email);
    void createRestaurant(RestaurantSignUpDto restaurantSignUpDto);
    void updateRestaurant(RestaurantUpdateDto restaurantUpdateDto);
    void putBestSellers(List<Integer> dishIds, Integer restaurantId);
    void createDish(DishDto dishDto);
    void updateDish(DishDto dishDto);
    DishDto findDishById(Integer dishId);
    ComboDto findComboById(Integer comboId);
    void createCombo(ComboDto comboDto);
    void updateCombo(ComboDto comboDto);
    RestaurantProfileDto findRestaurantProfileById(Integer restaurantId);
    Integer getRestaurantIdByEmail(String email);
}
