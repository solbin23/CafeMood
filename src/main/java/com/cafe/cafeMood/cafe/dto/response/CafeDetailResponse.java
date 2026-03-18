package com.cafe.cafeMood.cafe.dto.response;

import com.cafe.cafeMood.cafe.domain.cafe.Cafe;
import com.cafe.cafeMood.menu.dto.response.MenuResponse;

import java.util.List;
import java.util.Map;

public record CafeDetailResponse(
        Long cafeId,
        String cafeName,
        String address,
        String phone,
        String description,
        List<String> moodTags,
        Map<String, List<MenuResponse>> menus
) {

    public static CafeDetailResponse of(Cafe cafe,
                                        List<String> moodTags,
                                        Map<String, List<MenuResponse>> menus

    ) {
        return new CafeDetailResponse(
                cafe.getId(),
                cafe.getName(),
                cafe.getAddress(),
                cafe.getPhone(),
                cafe.getShortDesc(),
                moodTags,
                menus
        );
    }
}
