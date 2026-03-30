package com.cafe.cafeMood.review.dto.response;

import com.cafe.cafeMood.review.domain.CafeReview;

import java.time.LocalDateTime;
import java.util.List;

public record CafeReviewResponse(
        Long reviewId,
        Long userId,
        String writerName,
        String content,
        List<Long> tags,
        LocalDateTime createdAt,
        LocalDateTime updateAt
) {

}
