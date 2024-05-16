package com.javaweb.converter;

import com.javaweb.model.dto.OrderLineComboDto;
import com.javaweb.model.entity.OrderEntity;
import com.javaweb.model.entity.OrderLinesComboEntity;
import com.javaweb.model.request.OrderLineComboRequestDto;
import com.javaweb.repository.ComboRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderLineComboConverter {

    @Autowired
    private ComboRepository comboRepository;

    public OrderLineComboDto entityToDto(OrderLinesComboEntity orderLinesComboEntity) {
        OrderLineComboDto orderLineComboDto = new OrderLineComboDto();
        orderLineComboDto.setQuantity(orderLinesComboEntity.getQuantity());
        orderLineComboDto.setComboName(orderLinesComboEntity.getComboEntity().getName());
        orderLineComboDto.setPrice(orderLineComboDto.getPrice());
        return orderLineComboDto;
    }

    public OrderLinesComboEntity dtoToEntity(OrderLineComboRequestDto orderLineComboDto, OrderEntity orderEntity) {
        OrderLinesComboEntity orderLinesComboEntity = new OrderLinesComboEntity();
        orderLinesComboEntity.setQuantity(orderLineComboDto.getQuantity());
        orderLinesComboEntity.setOrderEntity(orderEntity);
        orderLinesComboEntity.setComboEntity(comboRepository.findById(orderLineComboDto.getComboId()).get());
        return orderLinesComboEntity;
    }
}
