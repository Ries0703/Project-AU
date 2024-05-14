package com.javaweb.service.impl;

import com.javaweb.converter.*;
import com.javaweb.enums.VipType;
import com.javaweb.model.dto.*;
import com.javaweb.model.entity.CustomerEntity;
import com.javaweb.repository.CustomerRepository;
import com.javaweb.repository.FeedbackRepository;
import com.javaweb.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerConverter customerConverter;

    @Autowired
    private OrderConverter orderConverter;

    @Autowired
    private OrderLineDishConverter orderLineDishConverter;

    @Autowired
    private OrderLineComboConverter orderLineComboConverter;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private FeedbackConverter feedbackConverter;

    @Override
    public List<CustomerEntity> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public boolean existCustomer(String email, String password) {
        return customerRepository.existsByEmailAndPassword(email, password);
    }

    @Override
    public boolean existEmail(String email) {
        return customerRepository.existsByEmail(email);
    }

    @Override
    public void createCustomer(CustomerSignUpDto customerSignUpDto) {
        customerRepository.save(customerConverter.signUpDtoToEntity(customerSignUpDto, new CustomerEntity()));
    }

    @Override
    public boolean registerMembership(MembershipDto membershipDto) {
        CustomerEntity customerEntity = customerRepository.findById(membershipDto.getCustomerId()).get();
        LocalDate currentExpiration = customerEntity.getVipExpiration();
        if (currentExpiration != null && LocalDate.now().isBefore(currentExpiration)) {
            return false;
        }
        customerEntity.setVipType(membershipDto.getVipType().getVipTypeName());
        int days = membershipDto.getVipType().equals(VipType.MONTHLY) ? 30 : 365;
        customerEntity.setVipExpiration(
                Optional.ofNullable(currentExpiration)
                        .map(expiration -> expiration.plusDays(days))
                        .orElse(LocalDate.now().plusDays(days))
        );
        customerRepository.save(customerEntity);
        return true;
    }

    @Override
    public CustomerProfileDto getCustomerProfile(Integer customerId) {
        CustomerEntity customerEntity = customerRepository.findById(customerId).get();

        CustomerDto customerDto = customerConverter.entityToDto(customerEntity);
        List<OrderDto> orderDtoList = customerEntity.getOrderEntityList().stream()
                .map(orderConverter::entityToDto)
                .collect(Collectors.toList());

        CustomerProfileDto customerProfileDto = new CustomerProfileDto();
        customerProfileDto.setCustomerDto(customerDto);
        customerProfileDto.setOrderDtoList(orderDtoList);
        return customerProfileDto;
    }

    @Override
    public void giveFeedback(FeedbackDto feedbackDto) {
        feedbackRepository.save(feedbackConverter.dtoToEntity(feedbackDto));
    }
}
