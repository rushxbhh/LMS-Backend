package com.edu.lms.auth.dto;

import lombok.*;

@Data
public class RegisterRequest {
    private String email;
    private String name;
    private String providerId;
    private String avatarUrl;
}