package com.javaweb.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RestaurantSearchRequest {
    private String keyword;
    private String category;
    private Integer distanceFrom;
    private Integer distanceTo;
    private Double ratingFrom;
    private Double ratingTo;
}
