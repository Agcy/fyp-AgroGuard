package com.api.agroguard.service;

import com.api.agroguard.entity.DetectionResponse;
import com.api.agroguard.model.DetectionResultDO;

import java.io.File;
import java.io.IOException;

public interface DetectionService {
    DetectionResponse detectAndSave(File imageFile, String userId) throws IOException;
    DetectionResultDO getDetectionResult(String userId);
}
