package com.api.agroguard.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "detection_results")
public class DetectionResultDO {
    @Id
    private String id;
    private String userId; // 假设每个检测结果都关联到一个用户
    private String sourceImageUrl;
    private String detectionResult;
    private LocalDateTime gmtDetected;
}
