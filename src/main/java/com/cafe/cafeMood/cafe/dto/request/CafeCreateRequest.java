package com.cafe.cafeMood.cafe.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CafeCreateRequest(

        @NotBlank(message = "카페명은 필수입니다.")
        @Size(max = 100, message = "카페명은 100자를 초과할 수 없습니다.")
        String name,

        @NotBlank
        @Size(max = 255)
        String address,

        @Size(max = 255)
        String shortDesc,

        @NotBlank(message = "전화번호는 필수입니다.")
        @Size(max = 30, message = "옳바르게 입력해주세요.")
        String phoneNumber

) {
}
