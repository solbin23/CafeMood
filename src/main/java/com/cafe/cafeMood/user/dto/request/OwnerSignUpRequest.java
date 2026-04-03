package com.cafe.cafeMood.user.dto.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record OwnerSignUpRequest(@Email @NotBlank String email,
                                 @NotBlank String password,
                                 @NotBlank String name,
                                 @NotBlank String phone,
                                 @NotBlank @Pattern(regexp = "\\d{10}",message = "사업자등록번호는 숫자 10자리여야 합니다.")
                                 String businessNumber,

                                 @NotBlank
                                 String businessName,
                                 @AssertTrue(message = "서비스 이용약관 동의는 필수입니다.")
                                 boolean serviceTermsConsent,
                                 @AssertTrue(message = "개인정보 수집 및 이용 동의는 필수입니다.")
                                 boolean privacyConsent,
                                 boolean marketingConsent) {
}
