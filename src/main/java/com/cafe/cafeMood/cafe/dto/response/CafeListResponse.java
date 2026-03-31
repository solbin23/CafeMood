package com.cafe.cafeMood.cafe.dto.response;

import com.cafe.cafeMood.cafe.domain.cafe.CafeStatus;

import java.util.List;

public record CafeListResponse(
        Long cafeId,
        String name,
        String address,
        List<String> tags,
        CafeStatus status
) {
}
