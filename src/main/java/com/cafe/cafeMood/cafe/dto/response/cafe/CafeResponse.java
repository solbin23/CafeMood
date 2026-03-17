package com.cafe.cafeMood.cafe.dto.response.cafe;

import com.cafe.cafeMood.cafe.domain.cafe.Cafe;
import com.cafe.cafeMood.cafe.domain.cafe.CafeStatus;

import java.time.LocalDateTime;


public record CafeResponse(Long id,
                           Long ownerId,
                           String name,
                           String address,
                           String phone,
                           String shortDesc,
                           CafeStatus status,
                           LocalDateTime createDate,
                           LocalDateTime updateDate) {

    public static CafeResponse from(Cafe cafe) {
        return new CafeResponse(
                cafe.getId(),
                cafe.getOwnerId(),
                cafe.getName(),
                cafe.getAddress(),
                cafe.getPhone(),
                cafe.getShortDesc(),
                cafe.getStatus(),
                cafe.getCreatedAt(),
                cafe.getUpdatedAt()
        );
    }
}


