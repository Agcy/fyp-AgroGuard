package com.api.agroguard.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String id;
    private String username;
    private String avatarUrl;
    private boolean isOnline;

    // Constructors, getters, and setters
    public UserDTO(String id, String username, String avatarUrl, boolean isOnline) {
        this.id = id;
        this.username = username;
        this.avatarUrl = avatarUrl;
        this.isOnline = isOnline;
    }
}
