package com.javaweb.converter;

import com.javaweb.model.dto.FeedbackDto;
import com.javaweb.model.entity.CustomerEntity;
import com.javaweb.model.entity.FeedbackEntity;
import com.javaweb.model.entity.RestaurantEntity;
import com.javaweb.repository.CustomerRepository;
import com.javaweb.repository.RestaurantRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FeedbackConverter {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    public FeedbackEntity dtoToEntity(FeedbackDto feedbackDto) {
        FeedbackEntity feedbackEntity = new FeedbackEntity();
        feedbackEntity.setStars(feedbackDto.getStars());
        feedbackEntity.setContent(feedbackDto.getContent());
        CustomerEntity customerEntity = customerRepository.findById(feedbackDto.getCustomerId()).get();
        RestaurantEntity restaurantEntity = restaurantRepository.findById(feedbackDto.getRestaurantId()).get();
        feedbackEntity.setFeedbackOwner(customerEntity);
        feedbackEntity.setRestaurantEntity(restaurantEntity);
        return feedbackEntity;
    }
}
