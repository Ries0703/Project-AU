package com.javaweb.model.response;

import lombok.Data;

@Data
public class RestaurantLoginResponse {
    private final String message;
    private final Integer restaurantId;
}
