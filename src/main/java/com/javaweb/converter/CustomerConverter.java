package com.javaweb.converter;

import com.javaweb.model.dto.CustomerDto;
import com.javaweb.model.dto.CustomerSignUpDto;
import com.javaweb.model.entity.CustomerEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerConverter {

    @Autowired
    ModelMapper modelMapper;

    public CustomerEntity signUpDtoToEntity(CustomerSignUpDto customerSignUpDto, CustomerEntity customerEntity) {
        customerEntity = modelMapper.map(customerSignUpDto, CustomerEntity.class);
        return customerEntity;
    }

    public CustomerDto entityToDto(CustomerEntity customerEntity) {
        return modelMapper.map(customerEntity, CustomerDto.class);
    }
}
