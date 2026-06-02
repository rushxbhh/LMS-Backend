package com.edu.lms.user.controller;

import com.edu.lms.common.response.ApiResponse;
import com.edu.lms.user.dto.UpdateProfileRequest;
import com.edu.lms.user.dto.UserDto;
import com.edu.lms.user.entity.User;
import com.edu.lms.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "User profile management")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    @Operation(summary = "Get own profile")
    public ResponseEntity<ApiResponse<UserDto>> getMyProfile(
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(
                ApiResponse.success("Profile fetched", UserDto.from(currentUser))
        );
    }

    @PutMapping("/me")
    @Operation(summary = "Update own profile")
    public ResponseEntity<ApiResponse<UserDto>> updateMyProfile(
            @AuthenticationPrincipal User currentUser,
            @Valid @RequestBody UpdateProfileRequest request) {
        UserDto updated = userService.updateProfile(currentUser.getId(), request);
        return ResponseEntity.ok(ApiResponse.success("Profile updated", updated));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get public profile of any user")
    public ResponseEntity<ApiResponse<UserDto>> getUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success("User fetched", userService.getUserById(id)));
    }

    // ===== ADMIN ENDPOINTS =====

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "List all users (Admin only)")
    public ResponseEntity<ApiResponse<Page<UserDto>>> getAllUsers(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success("Users fetched", userService.getAllUsers(pageable)));
    }

    @PutMapping("/{id}/role")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Change a user's role (Admin only)")
    public ResponseEntity<ApiResponse<UserDto>> changeRole(
            @PathVariable UUID id,
            @RequestParam User.Role role) {
        return ResponseEntity.ok(ApiResponse.success("Role updated", userService.changeUserRole(id, role)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Deactivate a user (Admin only)")
    public ResponseEntity<ApiResponse<Void>> deactivateUser(@PathVariable UUID id) {
        userService.deactivateUser(id);
        return ResponseEntity.ok(ApiResponse.success("User deactivated"));
    }
}