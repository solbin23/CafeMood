package com.cafe.cafeMood.cafe.dto.response;

import com.cafe.cafeMood.cafe.domain.Cafe;
import com.cafe.cafeMood.cafe.domain.CafeStatus;

import java.time.Instant;

public record CafeResponse(Long cafeId,
                           String name,
                           String shortDesc,
                           CafeStatus cafeStatus,
                           Instant createDate,
                           Instant updateDate) {

    public static CafeResponse from(Cafe cafe) {
        return new CafeResponse(
                cafe.getId(),
                cafe.getName(),
                cafe.getShortDesc(),
                cafe.getStatus(),
                cafe.getCreateDate(),
                cafe.getUpdateDate()
        );
    }
}


