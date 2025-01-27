package com.district12.backend.controllers;

import com.district12.backend.dtos.UserRequest;
import com.district12.backend.dtos.UserResponse;
import com.district12.backend.entities.User;
import com.district12.backend.services.UserService;
import com.district12.backend.utils.SecurityUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
@Slf4j
public class UserControllerV1 {
    private final UserService userService;

    @GetMapping("/get-info")
    public ResponseEntity<UserResponse> getUserPersonalDetails() {
        Long userId = SecurityUtils.getOwnerID();
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(new UserResponse(user.getFullName(), user.getEmail(), user.getPhoneNumber()));
    }

    @PostMapping("/update-info")
    public ResponseEntity<Void> updateUserProfile(@Valid @RequestBody UserRequest request) {
        Long userId = SecurityUtils.getOwnerID();
        userService.updateUserInfo(userId, request.email(), request.phoneNumber());
        return ResponseEntity.noContent().build();
    }

}
