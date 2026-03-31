package com.cafe.cafeMood.cafe.dto.response;

public record CafeSearchFlat(
        Long cafeId,
        String cafeName,
        String address,
        Double totalScore
) {
}
