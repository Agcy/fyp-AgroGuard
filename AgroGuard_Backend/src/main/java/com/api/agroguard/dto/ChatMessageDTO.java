package com.api.agroguard.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatMessageDTO {
    private String id;
    private String senderId;
    private String chatRoomId;
    private String content;
    private LocalDateTime timestamp;

    // Constructors
    public ChatMessageDTO() {}

    public ChatMessageDTO(String id, String senderId, String chatRoomId, String content, LocalDateTime timestamp) {
        this.id = id;
        this.senderId = senderId;
        this.chatRoomId = chatRoomId;
        this.content = content;
        this.timestamp = timestamp;
    }
}
