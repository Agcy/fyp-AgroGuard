package com.api.agroguard.controller;

import com.api.agroguard.model.ImageDO;
import com.api.agroguard.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("/{userId}/upload")
    public ResponseEntity<ImageDO> uploadImage(@RequestBody ImageDO image) {
        return ResponseEntity.ok(imageService.saveImage(image));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImageDO> getImageById(@PathVariable String id) {
        return ResponseEntity.ok(imageService.getImageById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ImageDO>> getImagesByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(imageService.getImagesByUserId(userId));
    }

    @DeleteMapping("/{id}")
    public void deleteImage(@PathVariable String id) {
        imageService.deleteImage(id);
    }
}
