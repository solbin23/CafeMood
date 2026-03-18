package com.cafe.cafeMood.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignUpRequest(
        @NotBlank @Size(min = 10, max = 50)
        String loginId,
        @NotBlank @Size(min = 8, max = 20)
        String password,
        @NotBlank
        String name,
        @NotBlank @Email
        String email,
        @Size(max = 30)
        String phone
) {
}
