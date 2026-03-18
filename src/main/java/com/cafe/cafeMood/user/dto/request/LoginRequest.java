package com.cafe.cafeMood.user.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(@NotBlank String loginId,
                           @NotBlank String password) {
}
