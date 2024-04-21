package com.api.agroguard.entity;

import lombok.Data;

@Data
public class PriceData {
    private String date;
    private String type;
    private String category;
    private double index;
}
