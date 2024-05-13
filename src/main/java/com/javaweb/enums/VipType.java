package com.javaweb.enums;

import lombok.Getter;

@Getter
public enum VipType {
    MONTHLY("Monthly"), YEARLY("Yearly");
    private final String vipTypeName;
    VipType(String vipTypeName) {
        this.vipTypeName = vipTypeName;
    }
}
