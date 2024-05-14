package com.javaweb.converter;

import com.javaweb.model.dto.OrderLineDishDto;
import com.javaweb.model.entity.OrderLinesDishEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderLineDishConverter {

    @Autowired
    ModelMapper modelMapper;

    public OrderLineDishDto entityToDto(OrderLinesDishEntity orderLinesDishEntity) {
        OrderLineDishDto orderLineDishDto = new OrderLineDishDto();
        orderLineDishDto.setQuantity(orderLinesDishEntity.getQuantity());
        orderLineDishDto.setDishName(orderLinesDishEntity.getDishEntity().getName());
        return orderLineDishDto;
    }
}
