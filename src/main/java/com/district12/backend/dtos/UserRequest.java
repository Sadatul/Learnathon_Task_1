package com.district12.backend.dtos;

import jakarta.validation.constraints.NotNull;

public record UserRequest(
        @NotNull
        String email,

        @NotNull
        String phoneNumber
) {
}
