package com.cafe.cafeMood.common.auth;

import com.cafe.cafeMood.user.domain.UserRole;


public record LoginUser(
                        Long userId,
                        String email,
                        UserRole role) {

    public boolean isAdmin() {
        return role.equals(UserRole.ADMIN);
    }

    public boolean isUser() {
        return role.equals(UserRole.USER);
    }

    public boolean isOwner(){
        return role.equals(UserRole.OWNER);
    }

}
