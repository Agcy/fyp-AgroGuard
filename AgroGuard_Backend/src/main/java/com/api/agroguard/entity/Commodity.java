package com.api.agroguard.entity;

import lombok.Data;

@Data
public class Commodity {
    private String code;
    private String name;

    // 构造函数
    public Commodity(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
