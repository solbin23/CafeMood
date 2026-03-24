package com.cafe.cafeMood.common.auth.util;

import com.cafe.cafeMood.common.auth.dto.LoginUser;
import com.cafe.cafeMood.common.auth.jwt.JwtAuthFilter;
import com.cafe.cafeMood.common.exception.BusinessException;
import com.cafe.cafeMood.common.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;

public final class AuthUtil {

    private AuthUtil() {

    }

    public static LoginUser getLoginUser(HttpServletRequest request) {
        LoginUser loginUser = (LoginUser) request.getAttribute(JwtAuthFilter.LOGIN_USER_ATTRIBUTE);
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED);
        }

        return loginUser;
    }
}
