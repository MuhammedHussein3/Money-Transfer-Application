package com.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record RegistrationRequest(
        @NotNull(message = "FirstName must be required")
        String userName,

        @NotNull(message = "Email must be required")
        String email,

        @NotNull(message = "DateOfBirth must be required")
        LocalDate dateOfBirth,

        @NotBlank(message = "New password is required")
        String password

) {
}
