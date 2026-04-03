package com.cafe.cafeMood.user.dto.request;

import com.cafe.cafeMood.user.domain.UserRole;
import jakarta.validation.constraints.*;

public record UserSignUpRequest(
        @NotBlank @Email
        String email,
        @NotBlank(message = "비밀번호는 필수입니다.") @Size(min = 8, max = 20)
        String password,
        @NotBlank(message = "이름은 필수입니다.")
        String name,
        @Size(max = 30)
        String phone,

        @AssertTrue(message = "서비스 이용약관 동의는 필수입니다.")
        boolean serviceTermsConsent,
        @AssertTrue(message = "개인정보 수집 및 이용 동의는 필수입니다.")
        boolean privacyConsent,
        boolean marketingConsent
) {
}
