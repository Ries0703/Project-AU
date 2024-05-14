package com.javaweb.converter;

import com.javaweb.model.dto.OrderLineDishDto;
import com.javaweb.model.entity.OrderLinesDishEntity;
import org.springframework.stereotype.Component;

@Component
public class OrderLineDishConverter {

    public OrderLineDishDto entityToDto(OrderLinesDishEntity orderLinesDishEntity) {
        OrderLineDishDto orderLineDishDto = new OrderLineDishDto();
        orderLineDishDto.setQuantity(orderLinesDishEntity.getQuantity());
        orderLineDishDto.setDishName(orderLinesDishEntity.getDishEntity().getName());
        return orderLineDishDto;
    }
}
