package com.cafe.cafeMood.user.dto.response;

import com.cafe.cafeMood.user.domain.UserRole;

public record LoginResponse(
        Long userId,
        String loginId,
        UserRole role
) {

    public static LoginResponse of(Long userId, String loginId,UserRole role) {
        return new LoginResponse(userId, loginId,  role);
    }
}
