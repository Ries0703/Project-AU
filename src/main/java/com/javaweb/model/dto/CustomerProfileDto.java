package com.javaweb.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class CustomerProfileDto {
    private CustomerDto customerDto;
    private List<OrderDto> orderDtoList;
}
