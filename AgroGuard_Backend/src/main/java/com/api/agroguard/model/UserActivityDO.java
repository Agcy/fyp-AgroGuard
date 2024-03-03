package com.api.agroguard.model;

import org.springframework.data.annotation.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "user_activities")
public class UserActivityDO {
    @Id
    private String id;
    private String username;
    private String activity;
    private String ip;
    private LocalDateTime timestamp;

    // Constructors, Getters, and Setters
}
