package com.cafe.cafeMood.user.dto.request;

import com.cafe.cafeMood.user.domain.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SignUpRequest(
        @NotBlank @Email
        String email,
        @NotBlank(message = "비밀번호는 필수입니다.") @Size(min = 8, max = 20)
        String password,
        @NotBlank(message = "카페명은 필수입니다.")
        String name,
        @Size(max = 30)
        String phone,
        @NotNull(message = "권한은 필수입력입니다.")
        UserRole role
) {
}
