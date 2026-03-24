package com.cafe.cafeMood.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignUpRequest(
        @NotBlank @Email
        String email,
        @NotBlank @Size(min = 8, max = 20)
        String password,
        @NotBlank
        String name,
        @Size(max = 30)
        String phone
) {
}
