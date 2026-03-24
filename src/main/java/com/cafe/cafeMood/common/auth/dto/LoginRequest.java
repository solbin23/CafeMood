package com.cafe.cafeMood.common.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(@NotBlank String email,
                           @NotBlank String password) {
}
