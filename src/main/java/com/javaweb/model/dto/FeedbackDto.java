package com.javaweb.model.dto;

import lombok.Data;

@Data
public class FeedbackDto {
    private Integer customerId;
    private Integer restaurantId;
    private Integer stars;
    private String content;
}
