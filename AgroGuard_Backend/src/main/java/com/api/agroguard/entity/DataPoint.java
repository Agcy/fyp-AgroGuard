package com.api.agroguard.entity;

import lombok.Data;

@Data
public class DataPoint {
    private String reportMonth;
    private String year;
    private Double value;

    // 构造函数
    public DataPoint(String reportMonth, String year, Double value) {
        this.reportMonth = reportMonth;
        this.year = year;
        this.value = value;
    }
}
