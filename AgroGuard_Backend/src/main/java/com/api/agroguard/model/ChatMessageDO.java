package com.api.agroguard.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "chat_msg")
public class ChatMessageDO {
    @Id
    private String id;
    private String chatRoomId; // 聊天室ID
    private String senderId;
    private String content;
    private LocalDateTime timestamp;
}
