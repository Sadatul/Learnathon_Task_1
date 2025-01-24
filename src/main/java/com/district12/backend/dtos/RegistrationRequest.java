package com.district12.backend.dtos;

import com.district12.backend.enums.Role;
import jakarta.validation.constraints.*;

public record RegistrationRequest(
        @NotNull
        @Email
        String email,

        @Size(min=8, max = 32, message = "Password must be at least 6 and at most 32 characters long.")
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*(),.?\":{}|<>]).{2,}$", message = "A password must include at least one number and one special character.")
        @NotNull
        String password,

        @Size(min = 0, max = 255, message = "Full name must be at most 255 characters long.")
        @NotNull
        String fullName,

        @NotNull
        Role role,

        @Size(min = 11, max = 11, message = "Phone number must be 11 characters long.")
        String phoneNumber
) {
    @AssertTrue(message = "Role cannot be ADMIN")
    private boolean isValidRole() {
        return role != Role.ADMIN;
    }
}
