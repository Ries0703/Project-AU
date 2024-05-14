package com.javaweb.model.dto;

import com.javaweb.enums.VipType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CustomerDto {
    private String email;
    private VipType vipType;
    private LocalDate vipExpiration;
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
