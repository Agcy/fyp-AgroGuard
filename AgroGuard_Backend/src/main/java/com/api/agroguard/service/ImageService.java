package com.api.agroguard.service;

import com.api.agroguard.model.ImageDO;
import java.util.List;

public interface ImageService {
    ImageDO saveImage(ImageDO image);
    ImageDO getImageById(String id);
    List<ImageDO> getImagesByUserId(String userId);
    void deleteImage(String id);
}
