package com.javaweb.converter;

import com.javaweb.model.dto.OrderDto;
import com.javaweb.model.dto.OrderLineComboDto;
import com.javaweb.model.dto.OrderLineDishDto;
import com.javaweb.model.entity.OrderEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderConverter {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OrderLineComboConverter orderLineComboConverter;

    @Autowired
    private OrderLineDishConverter orderLineDishConverter;

    public OrderDto entityToDto(OrderEntity orderEntity) {
        OrderDto orderDto = modelMapper.map(orderEntity, OrderDto.class);
        List<OrderLineComboDto> orderLineComboDtoList = orderEntity.getOrderLinesComboEntityList().stream()
                .map(orderLineComboConverter::entityToDto)
                .collect(Collectors.toList());
        List<OrderLineDishDto> orderLineDishDtoList = orderEntity.getOrderLinesDishEntityList().stream()
                .map(orderLineDishConverter::entityToDto)
                .collect(Collectors.toList());
        orderDto.setOrderLineComboDtoList(orderLineComboDtoList);
        orderDto.setOrderLineDishDtoList(orderLineDishDtoList);
        return orderDto;
    }
}
