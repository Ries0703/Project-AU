package com.javaweb.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderDto {
    private Integer id;
    private String customerAddress;
    private Integer postCodeAddress;
    private String cardNumber;
    private List<OrderLineComboDto> orderLineComboDtoList;
    private List<OrderLineDishDto> orderLineDishDtoList;
}
