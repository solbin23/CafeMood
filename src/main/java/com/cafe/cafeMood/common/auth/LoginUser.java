package com.cafe.cafeMood.common.auth;

import com.cafe.cafeMood.user.domain.UserRole;

public record LoginUser(Long userId,
                        String email,
                        UserRole role) {

    public boolean isAdmin() {
        return role == UserRole.ADMIN;
    }
    public boolean isUser() {
        return role == UserRole.USER;
    }
    public boolean isOwner() {
        return role == UserRole.OWNER;
    }
}
