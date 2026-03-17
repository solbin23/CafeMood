package com.cafe.cafeMood.cafe.dto.request.cafe;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CafeUpdateRequest(

        @NotBlank
        @Size(max = 100)
        String name,

        @NotBlank
        @Size(max = 255)
        String address,

        @Size(max = 30)
        String phone,

        @Size(max = 255)
        String shortDesc
) {
}
