package com.cafe.cafeMood.cafe.dto.request;

import jakarta.validation.Valid;

import javax.xml.stream.Location;
import java.math.BigDecimal;

public record AdminCafeUpdateRequest(
        String name,
        String shortDesc,
        String phone,
        String instagramUrl,
        String websiteUrl,
        @Valid Location location
) {

    public record Location(
            String address1,
            String address2,
            String region1,
            String region2,
            String region3,
            BigDecimal latitude,
            BigDecimal longitude,
            String naverPlaceUrl,
            String kakaoPlaceUrl
    ) {}
}
