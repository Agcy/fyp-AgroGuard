package com.api.agroguard.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "chat_rooms")
public class ChatRoomDO {
    @Id
    private String id;
    private List<String> participants;
    private String name;
    private String creator;
    private LocalDateTime lastUpdated; // 聊天室的最后更新时间
    private LocalDateTime gmtCreated; // 聊天室的创建时间
}
