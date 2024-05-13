package com.javaweb.converter;

import com.javaweb.model.dto.RestaurantDto;
import com.javaweb.model.entity.CustomerEntity;
import com.javaweb.model.entity.FeedbackEntity;
import com.javaweb.model.entity.RestaurantEntity;
import com.javaweb.repository.CustomerRepository;
import com.javaweb.repository.RestaurantRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RestaurantConverter {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;

    public RestaurantDto entityToDto(RestaurantEntity restaurantEntity, Integer customerPostcode) {
        RestaurantDto restaurantDto = modelMapper.map(restaurantEntity, RestaurantDto.class);
        List<FeedbackEntity> feedbackEntityList = restaurantEntity.getFeedbackEntityList();

        if (feedbackEntityList != null && !feedbackEntityList.isEmpty()) {
            double avgStars = feedbackEntityList.stream()
                    .mapToInt(FeedbackEntity::getStars)
                    .average()
                    .orElse(0);
            restaurantDto.setStars(avgStars);
        } else {
            restaurantDto.setStars(0.0);
        }

        Integer distance = Math.abs(restaurantEntity.getPostCode() - customerPostcode);
        restaurantDto.setDistance(distance);
        return restaurantDto;
    }
}
