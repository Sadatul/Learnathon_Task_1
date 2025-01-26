package com.district12.backend.dtos;

public record UserResponse(
        String name,
        String email,
        String phoneNumber
) {
}
