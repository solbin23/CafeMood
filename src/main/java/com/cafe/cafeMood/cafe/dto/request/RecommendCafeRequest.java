package com.cafe.cafeMood.cafe.dto.request;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record RecommendCafeRequest(
        @NotEmpty
        List<Long> tagIds
) {
}
