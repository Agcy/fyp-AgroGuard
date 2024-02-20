package com.api.agroguard.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

// Image Collection
@Data
@Document
public class ImageDO {
    @Id
    private String id;
    private String userId;
    private LocalDateTime gmtUploaded;
    private String imagePath;
    private LocalDateTime gmtModified;
}
