package com.cafe.cafeMood.review.dto.response;

import com.cafe.cafeMood.review.domain.CafeReview;
import com.cafe.cafeMood.tag.domain.Tag;

import java.time.LocalDateTime;
import java.util.List;

public record CafeReviewResponse(
        Long reviewId,
        Long userId,
        String writerName,
        String content,
        List<String> tags,
        LocalDateTime createdAt,
        LocalDateTime updateAt
) {

    public static CafeReviewResponse from(CafeReview cafeReview, List<Tag> tags) {
        List<String> tagNames = tags.stream()
                .map(Tag::getName)
                .toList();

        return new CafeReviewResponse(
                cafeReview.getId(),
                cafeReview.getUser().getId(),
                cafeReview.getUser().getName(),
                cafeReview.getContent(),
                tagNames,
                cafeReview.getCreatedAt(),
                cafeReview.getUpdatedAt()
        );
    }


}
