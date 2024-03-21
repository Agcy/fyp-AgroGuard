package com.api.agroguard.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "comments")
public class CommentDO {
    @Id
    private String id;
    @NotBlank
    private String postId;
    @NotBlank
    private String userId;
    @NotBlank
    private String userName; // 用户名
    @NotBlank
    private String userAvatarUrl; // 用户头像URL
    @NotBlank
    private String content;
    @NotBlank
    private LocalDateTime createdAt;
    private String parentCommentId; // 可以为null
}
