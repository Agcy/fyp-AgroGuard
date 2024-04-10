package com.api.agroguard.entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class DetectionRequest {
    private String userId;
    private MultipartFile image;
}
