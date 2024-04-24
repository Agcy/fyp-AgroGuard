package com.api.agroguard.controller;

import com.api.agroguard.entity.DetectionResponse;
import com.api.agroguard.model.DetectionResultDO;
import com.api.agroguard.service.DetectionService;
import com.api.agroguard.service.impl.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/detection")
public class DetectionController {

    private final DetectionService detectionService;

    @Autowired
    public DetectionController(DetectionService detectionService) {
        this.detectionService = detectionService;
    }

    @PostMapping("/detect")
    public ResponseEntity<DetectionResponse> detect(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam("image") MultipartFile image) {
        File convFile = null;
        try {
            // 创建临时文件
            convFile = File.createTempFile("upload_", "_" + image.getOriginalFilename());
            image.transferTo(convFile);

            // 调用服务层进行检测并保存结果
            DetectionResponse result = detectionService.detectAndSave(convFile, userDetails.getId());

            return ResponseEntity.ok(result);
        } catch (IOException e) {
            // 记录异常信息，这里可以根据实际情况使用日志框架
            e.printStackTrace();
            // 返回错误响应
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } finally {
            // 清理临时文件
            if (convFile != null) {
                convFile.delete();
            }
        }
    }

    // 获取检测历史记录
    @GetMapping("/{userId}")
    public ResponseEntity<List<DetectionResultDO>> getDetectionResult(@PathVariable String userId) {
        List<DetectionResultDO> result = detectionService.getDetectionResult(userId);
        return ResponseEntity.ok(result);
    }


    // 转换方法的实现
}
