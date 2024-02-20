package com.api.agroguard.service.impl;

import com.api.agroguard.model.ImageDO;
import com.api.agroguard.repository.ImageRepository;
import com.api.agroguard.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public ImageDO saveImage(ImageDO image) {
        return imageRepository.save(image);
    }

    @Override
    public ImageDO getImageById(String id) {
        return imageRepository.findById(id).orElse(null);
    }

    @Override
    public List<ImageDO> getImagesByUserId(String userId) {
        return imageRepository.findByUserId(userId);
    }

    @Override
    public void deleteImage(String id) {
        imageRepository.deleteById(id);
    }
}
