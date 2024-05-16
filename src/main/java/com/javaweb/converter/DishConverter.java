package com.javaweb.converter;

import com.javaweb.model.dto.DishDto;
import com.javaweb.model.entity.DishEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DishConverter {
    @Autowired
    private ModelMapper modelMapper;

    public DishEntity dtoToEntity(DishDto dishDto) {
        return modelMapper.map(dishDto, DishEntity.class);
    }
    public DishDto entityToDto(DishEntity dishEntity) {
        DishDto dishDto = new DishDto();
        dishDto.setImage(dishEntity.getImage());
        dishDto.setName(dishEntity.getName());
        dishDto.setDescription(dishEntity.getDescription());
        dishDto.setPrice(dishEntity.getPrice());
        dishDto.setId(dishEntity.getId());
        dishDto.setRestaurantId(dishEntity.getRestaurantEntity().getId());
        return dishDto;
    }
}
