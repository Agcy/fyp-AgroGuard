package com.api.agroguard.model;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "posts")
public class PostDO {
    @Id
    private String id;

    @NotBlank
    private String title; // 帖子的标题
    @NotBlank
    @Size(min = 10)
    private String content; // 帖子的文字内容
    @NotBlank
    private List<String> base64Image;// 或者使用  如果你直接存储Base64编码的图片
    @NotBlank
    private String userId; // 发帖用户的ID
    @NotBlank
    private String userName;
    @NotBlank
    private String imageId;
    @NotBlank
    private LocalDateTime createdAt; // 发布时间
    @NotBlank
    private LocalDateTime updatedAt; // 修改时间
}
