package com.casestudy.myRetail.model.enums;

public enum CurrencyCodeEnum {

    INR("INR", "Indian rupee"),

    USD("USD", "US Dollar");

    private String code;

    private String name;

    CurrencyCodeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
