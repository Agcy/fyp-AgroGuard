package com.api.agroguard.entity;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

/*
* 这是用户发帖的实际发送请求
* */
@Data
public class PostRequest {
    @NotBlank
    private String title;
    @NotBlank
    @Size(min = 10)
    private String content;
    private List<String> base64Imgs;
}
