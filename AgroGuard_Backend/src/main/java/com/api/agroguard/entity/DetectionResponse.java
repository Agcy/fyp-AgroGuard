package com.api.agroguard.entity;

import lombok.Data;

@Data
public class DetectionResponse {
    private String title;
    private String description;
    private String prevent;
    private String image_url;
    private int pred;
    private String supplement_name;
    private String supplement_image_url;
    private String supplement_buy_link;
}
