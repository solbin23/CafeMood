package com.cafe.cafeMood.review.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CafeReviewCreateRequest(
        @NotNull
        Long cafeId,
        @NotNull
        Long userId,
        @NotNull
        Integer rating,
        @Size(max = 2000)
        String content,
        List<Long> tagIds
) {
}
