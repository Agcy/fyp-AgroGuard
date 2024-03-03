package com.api.agroguard.model;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

// Image Collection
@Data
@Document(collection = "images")
public class ImageDO {
    @Id
    private String id;
    @NotBlank
    private String userId;
    @NotBlank
    private LocalDateTime gmtUploaded;
    @NotBlank
    private String imageContent;
    @NotBlank
    private LocalDateTime gmtModified;
}
