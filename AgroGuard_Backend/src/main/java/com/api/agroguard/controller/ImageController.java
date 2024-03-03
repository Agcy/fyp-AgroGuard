package com.api.agroguard.controller;

import com.api.agroguard.entity.ImageRequest;
import com.api.agroguard.model.ImageDO;
import com.api.agroguard.service.ImageService;
import com.api.agroguard.service.impl.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("/{name}/upload")
    public ResponseEntity<ImageDO> uploadImage(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ImageRequest image) {
        ImageDO imageDO = new ImageDO();
        imageDO.setUserId(userDetails.getId()); // 或者获取用户的唯一标识符
        imageDO.setImageContent(image.getBase64Image());
        imageDO.setGmtUploaded(LocalDateTime.now());
        imageDO.setGmtModified(LocalDateTime.now());
        return ResponseEntity.ok(imageService.saveImage(imageDO));
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
