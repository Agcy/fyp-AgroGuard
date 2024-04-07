package com.api.agroguard.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ChatRoomRequest {
    private String name;
    private List<String> participants;

    public ChatRoomRequest() {}
}
