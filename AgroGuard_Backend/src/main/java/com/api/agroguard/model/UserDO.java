package com.api.agroguard.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

// User Collection
@Document(collection = "user")
@Data
public class UserDO {
    @Id
    private String id;
    private String occupation;
    private String gender;
    private String name;
    private String email;
    private String password;
    private LocalDateTime gmtCreated;
    private LocalDateTime gmtModified;

}
