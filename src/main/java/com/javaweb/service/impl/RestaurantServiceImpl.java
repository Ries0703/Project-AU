package com.javaweb.service.impl;

import com.javaweb.converter.RestaurantConverter;
import com.javaweb.model.dto.RestaurantDto;
import com.javaweb.model.request.RestaurantSearchRequest;
import com.javaweb.repository.RestaurantRepository;
import com.javaweb.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    RestaurantConverter restaurantConverter;

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