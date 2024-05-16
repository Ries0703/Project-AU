package com.javaweb.converter;

import com.javaweb.model.dto.OrderLineDishDto;
import com.javaweb.model.entity.OrderEntity;
import com.javaweb.model.entity.OrderLinesDishEntity;
import com.javaweb.model.request.OrderLineDishRequestDto;
import com.javaweb.repository.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderLineDishConverter {


    @Autowired
    private DishRepository dishRepository;

    public OrderLineDishDto entityToDto(OrderLinesDishEntity orderLinesDishEntity) {
        OrderLineDishDto orderLineDishDto = new OrderLineDishDto();
        orderLineDishDto.setQuantity(orderLinesDishEntity.getQuantity());
        orderLineDishDto.setDishName(orderLinesDishEntity.getDishEntity().getName());
        orderLineDishDto.setPrice(orderLineDishDto.getPrice());
        return orderLineDishDto;
    }

    public OrderLinesDishEntity dtoToEntity(OrderLineDishRequestDto orderLineDishDto, OrderEntity orderEntity) {
        OrderLinesDishEntity orderLinesDishEntity = new OrderLinesDishEntity();
        orderLinesDishEntity.setQuantity(orderLineDishDto.getQuantity());
        orderLinesDishEntity.setOrderEntity(orderEntity);
        orderLinesDishEntity.setDishEntity(dishRepository.findById(orderLineDishDto.getDishId()).get());
        return orderLinesDishEntity;
    }
}
