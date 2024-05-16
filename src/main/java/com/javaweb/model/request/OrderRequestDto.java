package com.javaweb.model.request;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderRequestDto {
    private Integer customerId;
    private Integer restaurantId;
    private Double total;
    private Integer customerPostcode;
    private String customerAddress;
    private String cardNumber;
    private String ccv;
    private String expiryDate;
    private List<OrderLineDishRequestDto> dishRequestDtoList = new ArrayList<>();
    private List<OrderLineComboRequestDto> comboRequestDtoList = new ArrayList<>();
}
