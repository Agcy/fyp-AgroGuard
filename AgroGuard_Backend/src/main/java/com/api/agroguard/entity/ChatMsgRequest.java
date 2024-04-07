package com.api.agroguard.entity;


import lombok.Data;

@Data
public class ChatMsgRequest {
    private String chatRoomId;
    private String senderId;
    private String content;
}
