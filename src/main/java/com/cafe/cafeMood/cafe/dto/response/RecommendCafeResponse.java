package com.cafe.cafeMood.cafe.dto.response;

public record RecommendCafeResponse(
        Long cafeId,
        String cafeName,
        String address,
        Double totalScore
) {
}
