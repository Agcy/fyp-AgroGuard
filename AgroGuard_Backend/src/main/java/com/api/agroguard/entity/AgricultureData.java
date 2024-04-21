package com.api.agroguard.entity;

import lombok.Data;

@Data
public class AgricultureData {
    private String code;
    private String report_month;
    private String region;
    private String commodity;
    private String item;
    private String year;
    private String period;
    private Double value;
    private Double min_value;
    private Double max_value;
}
