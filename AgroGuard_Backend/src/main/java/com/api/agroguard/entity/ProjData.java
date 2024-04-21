package com.api.agroguard.entity;

import lombok.Data;

@Data
public class ProjData {
    private String code;
    private String report_month;
    private String region;
    private String commodity;
    private String item;
    private String measure;
    private Double value;
}
