package com.edu.lms.auth.controller;

import com.edu.lms.auth.dto.AuthResponse;
import com.edu.lms.auth.dto.LoginRequest;
import com.edu.lms.auth.dto.RegisterRequest;
import com.edu.lms.auth.service.GoogleAuthServiceImpl;
import com.edu.lms.auth.service.OAuth2UserService;
import com.edu.lms.common.exception.BusinessException;
import com.edu.lms.common.response.ApiResponse;
import com.edu.lms.common.util.JwtUtil;
import com.edu.lms.user.entity.User;
import com.edu.lms.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final OAuth2UserService oAuth2UserService;
    private final GoogleAuthServiceImpl googleAuthService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // ===== Existing OAuth2 endpoints (unchanged) =====

    @PostMapping("/register")
    public User registerOAuth2User(@RequestBody RegisterRequest request) {
        return oAuth2UserService.registerNewOAuth2User(
                request.getEmail(), request.getName(),
                request.getProviderId(), request.getAvatarUrl());
    }

    @PostMapping("/google/login")
    public String loginWithGoogle(@RequestParam String code) {
        return googleAuthService.login(code);
    }

    // ===== NEW: local email/password login =====

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @RequestBody LoginRequest request) {

        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword())
            );
            User user = (User) auth.getPrincipal();
            return ResponseEntity.ok(ApiResponse.success("Login successful", buildAuthResponse(user)));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid email or password");
        }
    }

    // ===== NEW: local email/password registration =====

    @PostMapping("/register/local")
    public ResponseEntity<ApiResponse<AuthResponse>> registerLocal(
            @RequestBody LocalRegisterRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new BusinessException("An account with this email already exists");
        }

        User user = userRepository.save(User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(User.Role.STUDENT)
                .provider(User.Provider.LOCAL)
                .build());

        return ResponseEntity.ok(ApiResponse.success("Account created", buildAuthResponse(user)));
    }

    // ===== Helper =====

    private AuthResponse buildAuthResponse(User user) {
        return AuthResponse.builder()
                .token(jwtUtil.generateAccessToken(user))
                .refreshToken(jwtUtil.generateRefreshToken(user))
                .userId(user.getId().toString())
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole().name())
                .avatarUrl(user.getAvatarUrl())
                .build();
    }

    public record LocalRegisterRequest(String name, String email, String password) {}
}