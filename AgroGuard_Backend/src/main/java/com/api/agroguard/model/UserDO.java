package com.api.agroguard.model;

import com.api.agroguard.entity.Role;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// User Collection
@Document(collection = "user")
@Data
public class UserDO {
    @Id
    private String id;

    @NotBlank
    private String occupation;

    @NotBlank
    private String gender;

    @NotBlank
    @Size(max = 20)
    private String name;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @NotBlank
    private LocalDateTime gmtCreated;

    @NotBlank
    private LocalDateTime gmtModified;

    private String resetToken;

    @DBRef
    private Set<Role> roles = new HashSet<>();

    public UserDO(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

}
