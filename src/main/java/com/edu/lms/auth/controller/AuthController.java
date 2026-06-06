package com.edu.lms.auth.controller;

import com.edu.lms.auth.dto.RegisterRequest;
import com.edu.lms.auth.service.GoogleAuthServiceImpl;
import com.edu.lms.user.entity.User;
import com.edu.lms.auth.service.OAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private OAuth2UserService oAuth2UserService;

    @Autowired
    private GoogleAuthServiceImpl googleAuthService;

    @PostMapping("/register")
    public User registerOAuth2User(@RequestBody RegisterRequest request) {
        return oAuth2UserService.registerNewOAuth2User(
                request.getEmail(),
                request.getName(),
                request.getProviderId(),
                request.getAvatarUrl()
        );
    }

    @PostMapping("/google/login")
    public String loginWithGoogle(@RequestParam String code) {
        return googleAuthService.login(code);
    }
}