package com.javaweb.model.dto;

import lombok.Data;

@Data
public class OrderLineComboDto {
    private String comboName;
    private Integer quantity = 1;
    private Double price = 0.0;
}
