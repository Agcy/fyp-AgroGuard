package com.api.agroguard.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;
import java.util.List;

// Disease Collection
@Document(collection = "diseases")
@Data
public class DiseaseDO {
    @Id
    private String id;
    private String index;
    private String disease_name;
    private String description;
    private String prevent;
    private List<String> image_url;
}
