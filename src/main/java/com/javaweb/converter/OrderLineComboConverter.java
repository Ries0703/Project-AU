package com.javaweb.converter;

import com.javaweb.model.dto.OrderLineComboDto;
import com.javaweb.model.entity.OrderLinesComboEntity;
import org.springframework.stereotype.Component;

@Component
public class OrderLineComboConverter {

    public OrderLineComboDto entityToDto(OrderLinesComboEntity orderLinesComboEntity) {
        OrderLineComboDto orderLineComboDto = new OrderLineComboDto();
        orderLineComboDto.setQuantity(orderLinesComboEntity.getQuantity());
        orderLineComboDto.setComboName(orderLinesComboEntity.getComboEntity().getName());
        return orderLineComboDto;
    }
}
