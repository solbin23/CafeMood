package com.cafe.cafeMood.cafe.dto.response.cafe;

import com.cafe.cafeMood.cafe.domain.cafe.Cafe;
import com.cafe.cafeMood.menu.dto.response.TopMenuResponse;

import java.util.List;

public record CafeSearchResponse(
        Long cafeId,
        String cafeName,
        String address,
        List<String> moodTags,
        List<TopMenuResponse> coffeeMenus,
        List<TopMenuResponse> dessertMenus
) {
    public static CafeSearchResponse of(
            Cafe cafe,
            List<String> moodTags,
            List<TopMenuResponse> coffeeMenus,
            List<TopMenuResponse> dessertMenus
    ){
        return new CafeSearchResponse(
                cafe.getId(),
                cafe.getName(),
                cafe.getAddress(),
                moodTags,
                coffeeMenus,
                dessertMenus
        );
    }
}
