package com.cafe.cafeMood.user.dto.response;

import com.cafe.cafeMood.user.domain.User;
import com.cafe.cafeMood.user.domain.UserRole;

public record UserResponse(
        Long id,
        String loginId,
        String name,
        String email,
        String phone,
        UserRole role
) {
    public static UserResponse from(User user) {
        return new UserResponse(user.getId(),
        user.getLoginId(),
        user.getName(),
        user.getEmail(),
        user.getPhone(),
        user.getRole());
    }
}
