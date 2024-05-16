package com.javaweb.model.request;

import lombok.Data;

@Data
public class OrderLineComboRequestDto {
    private Integer comboId;
    private Integer quantity;
}
