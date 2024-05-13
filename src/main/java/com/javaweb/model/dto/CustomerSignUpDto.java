package com.javaweb.model.dto;

import lombok.Data;

@Data
public class CustomerSignUpDto {
    final String email;
    final String password;
    final String firstName;
    final String lastName;
    final String phoneNumber;
}
