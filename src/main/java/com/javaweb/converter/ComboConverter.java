package com.javaweb.converter;

import com.javaweb.model.dto.ComboDto;
import com.javaweb.model.entity.ComboEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ComboConverter {
    @Autowired
    private ModelMapper modelMapper;

    public ComboEntity dtoToEntity(ComboDto comboDto) {
        return modelMapper.map(comboDto, ComboEntity.class);
    }
    public ComboDto entityToDto(ComboEntity comboEntity) {
        return modelMapper.map(comboEntity, ComboDto.class);
    }
}