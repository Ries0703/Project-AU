package com.javaweb.service.impl;

import com.javaweb.converter.CustomerConverter;
import com.javaweb.enums.VipType;
import com.javaweb.model.dto.CustomerSignUpDto;
import com.javaweb.model.dto.MembershipDto;
import com.javaweb.model.entity.CustomerEntity;
import com.javaweb.repository.CustomerRepository;
import com.javaweb.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerConverter customerConverter;

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
    public void registerMembership(MembershipDto membershipDto) {
        CustomerEntity customerEntity = customerRepository.findById(membershipDto.getCustomerId()).get();
        customerEntity.setVipType(membershipDto.getVipType().getVipTypeName());
        int days = membershipDto.getVipType().equals(VipType.MONTHLY) ? 30 : 365;
        LocalDate currentExpiration = customerEntity.getVipExpiration();

        customerEntity.setVipExpiration(
                Optional.ofNullable(currentExpiration)
                        .map(expiration -> expiration.plusDays(days))
                        .orElse(LocalDate.now().plusDays(days))
        );
        customerRepository.save(customerEntity);
    }
}
