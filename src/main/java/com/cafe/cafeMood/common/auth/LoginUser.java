package com.cafe.cafeMood.common.auth;

import com.cafe.cafeMood.user.domain.UserRole;

public record LoginUser(Long userId,
                        String email,
                        UserRole role) {
}
