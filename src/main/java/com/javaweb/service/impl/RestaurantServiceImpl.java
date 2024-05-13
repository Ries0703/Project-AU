package com.javaweb.service.impl;

import com.javaweb.converter.RestaurantConverter;
import com.javaweb.model.dto.RestaurantDto;
import com.javaweb.repository.RestaurantRepository;
import com.javaweb.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    RestaurantConverter restaurantConverter;

    @Override
    public List<RestaurantDto> getRestaurantByName(String name, Integer customerPostcode) {
        return restaurantRepository.findByNameContains(name).stream()
                .map(restaurantEntity -> restaurantConverter.entityToDto(restaurantEntity, customerPostcode))
                .sorted(Comparator.comparing(RestaurantDto::getDistance))
                .collect(Collectors.toList());
    }

    @Override
    public List<RestaurantDto> getAllRestaurants(Integer customerPostcode) {
        return restaurantRepository.findAll().stream()
                .map(restaurantEntity -> restaurantConverter.entityToDto(restaurantEntity, customerPostcode))
                .sorted(Comparator.comparing(RestaurantDto::getDistance))
                .collect(Collectors.toList());
    }
}
// da co List<RestaurantEntity>