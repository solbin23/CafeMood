package com.cafe.cafeMood.cafe.dto.response;

import com.cafe.cafeMood.cafe.domain.cafe.Cafe;
import com.cafe.cafeMood.cafe.domain.cafe.CafeStatus;


import java.time.Instant;

public record AdminCafeResponse(Long id, String name, String shortDesc, CafeStatus status, Instant createDate, Instant updateDate) {

    public static AdminCafeResponse from(Cafe cafe){
        return new AdminCafeResponse(
                cafe.getId(),
                cafe.getName(),
                cafe.getShortDesc(),
                cafe.getStatus(),
                cafe.getCreateDate(),
                cafe.getUpdateDate()
        );
    }

}
