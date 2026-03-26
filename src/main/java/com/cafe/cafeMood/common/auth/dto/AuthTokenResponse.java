package com.cafe.cafeMood.common.auth.dto;

import com.cafe.cafeMood.user.domain.UserRole;

public record AuthTokenResponse(Long userId,
                                String email,
                                UserRole role,
                                String accessToken,
                                String refreshToken) {
}
