package com.api.agroguard.entity;

import lombok.Data;

@Data
public class TimeSeriesData {
    private String year;
    private Double value;

    // 构造函数
    public TimeSeriesData(String year, Double value) {
        this.year = year;
        this.value = value;
    }
}
