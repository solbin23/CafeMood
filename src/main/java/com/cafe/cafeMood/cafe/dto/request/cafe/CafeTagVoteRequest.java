package com.cafe.cafeMood.cafe.dto.request.cafe;

import jakarta.validation.constraints.NotNull;

public record CafeTagVoteRequest(
        @NotNull(message = "userId는 필수입니다.")
        Long tagId) {
}
