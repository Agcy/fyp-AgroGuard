package com.api.agroguard.service.impl;

import com.api.agroguard.entity.DetectionResponse;
import com.api.agroguard.model.DetectionResultDO;
import com.api.agroguard.repository.DetectionResultRepository;
import com.api.agroguard.service.DetectionService;
import com.api.agroguard.utils.FileUploadUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DetectionServiceImpl implements DetectionService {

    private final RestTemplate restTemplate;
    private final DetectionResultRepository repository;

    @Autowired
    public DetectionServiceImpl(RestTemplate restTemplate, DetectionResultRepository repository) {
        this.restTemplate = restTemplate;
        this.repository = repository;
    }

    @Override
    public DetectionResponse detectAndSave(File imageFile, String userId) throws IOException {
        String url = "http://localhost:5000/submit";

        // 准备请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // 准备请求体，包括文件资源
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", new FileSystemResource(imageFile));

        // 构造请求实体
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // 发送请求并接收响应
        ResponseEntity<DetectionResponse> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, DetectionResponse.class);

        // 解析响应并存储结果（这里假设响应体是直接可用的，实际上可能需要解析JSON）
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Detection failed");
        }
        try {
            String base64EncodedString = FileUploadUtil.encodeFileToBase64Binary(imageFile);
            DetectionResultDO result = new DetectionResultDO();
            // 假设response.getBody()返回了需要的数据，实际应用中可能需要从JSON解析
            result.setUserId(userId);
            result.setDetectionResult(response.getBody().getTitle());
            result.setSourceImageUrl(base64EncodedString);
            System.out.println(response.getBody());
            result.setGmtDetected(LocalDateTime.now());
            repository.save(result);
            // 使用Base64编码的字符串...
        } catch (IOException e) {
            e.printStackTrace();
            // 处理异常...
        }


        // 保存到数据库并返回
        return response.getBody();
    }

    @Override
    public List<DetectionResultDO> getDetectionResult(String userId) {
        return repository.findByUserId(userId);
    }
}
