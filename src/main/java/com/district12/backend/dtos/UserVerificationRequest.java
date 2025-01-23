package com.district12.backend.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserVerificationRequest(
        @NotNull
        @Email
        String email,

        @Size(min = 6, max = 6, message = "OTP must be 6 characters long.")
        @NotNull
        String otp
) {
}
