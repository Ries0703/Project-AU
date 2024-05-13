package com.javaweb.model.dto;

import com.javaweb.enums.VipType;
import lombok.Data;

@Data
public class MembershipDto {
    Integer customerId;
    VipType vipType;
}
