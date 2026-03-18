package com.cafe.cafeMood.review.dto.response;

import com.cafe.cafeMood.review.domain.CafeReview;

import java.time.LocalDateTime;
import java.util.List;

public record CafeReviewResponse(
        Long reviewId,
        Long cafeId,
        Long userId,
        Integer rating,
        String content,
        List<Long> tagIds,
        LocalDateTime createdAt,
        LocalDateTime updateAt
) {

    public static CafeReviewResponse of(CafeReview cafeReview, List<Long> tagIds) {
        return new CafeReviewResponse(
                cafeReview.getId(),
                cafeReview.getCafeId(),
                cafeReview.getUserId(),
                cafeReview.getRating(),
                cafeReview.getContent(),
                tagIds,
                cafeReview.getCreatedAt(),
                cafeReview.getUpdatedAt()
        );
    }
}
