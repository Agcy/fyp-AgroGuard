package com.api.agroguard.entity;

import lombok.Data;

@Data
public class CommentRequest {

    private String postId;
    private String content;
}
