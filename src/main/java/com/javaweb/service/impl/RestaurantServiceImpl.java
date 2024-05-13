package com.javaweb.service.impl;

import com.javaweb.converter.RestaurantConverter;
import com.javaweb.model.dto.RestaurantDto;
import com.javaweb.model.request.RestaurantSearchRequest;
import com.javaweb.repository.RestaurantRepository;
import com.javaweb.service.RestaurantService;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
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

    @Override
    public List<RestaurantDto> getRestaurantByParams(Map<String, Object> params, Integer customerPostcode) {
        RestaurantSearchRequest request = restaurantConverter.paramsToRequest(params);
        return restaurantRepository.findByNameContainsAndCategoryContains(request.getKeyword(), request.getCategory()).stream()
                .map(restaurantEntity -> restaurantConverter.entityToDto(restaurantEntity, customerPostcode))
                .filter(restaurantDto -> restaurantDto.getDistance() >= request.getDistanceFrom()
                        && restaurantDto.getDistance() <= request.getDistanceTo()
                        && restaurantDto.getStars() >= request.getRatingFrom()
                        && restaurantDto.getStars() <= request.getRatingTo())
                .collect(Collectors.toList());
    }
}