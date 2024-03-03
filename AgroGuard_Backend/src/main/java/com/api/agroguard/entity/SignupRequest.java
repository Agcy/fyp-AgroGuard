package com.api.agroguard.entity;

import java.util.Set;

import jakarta.validation.constraints.*;
import lombok.Data;


@Data
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String name;

    private String gender;
    private String occupation;
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    private Set<String> roles;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
}
