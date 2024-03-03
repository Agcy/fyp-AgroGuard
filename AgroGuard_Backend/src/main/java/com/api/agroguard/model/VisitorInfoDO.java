package com.api.agroguard.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "visitor_info")
public class VisitorInfoDO {
    @Id
    private String id;
    private String ip;
    private String geoLocation;
    private int visitCount;
}
