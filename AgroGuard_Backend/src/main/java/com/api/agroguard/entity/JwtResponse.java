package com.api.agroguard.entity;

import lombok.Data;

import java.util.List;

@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String id;
    private String username;
    private String email;
    private List<String> roles;
    private List<String> following;
    private List<String> followers;
    private List<String> likedPosts;
    private String avatarUrl;
    private String occupation;

    public JwtResponse(String accessToken, String id, String username, String email, List<String> roles, List<String> following, List<String> followers, List<String> likedPosts, String avatarUrl, String occupation) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.following = following;
        this.followers = followers;
        this.likedPosts = likedPosts;
        this.avatarUrl = avatarUrl;
        this.occupation = occupation;
    }
}
