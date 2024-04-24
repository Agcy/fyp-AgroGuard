package com.api.agroguard.service;

import com.api.agroguard.entity.DetectionResponse;
import com.api.agroguard.model.DetectionResultDO;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface DetectionService {
    DetectionResponse detectAndSave(File imageFile, String userId) throws IOException;
    List<DetectionResultDO> getDetectionResult(String userId);
}
