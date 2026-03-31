package com.cafe.cafeMood.cafe.dto.response;

import com.cafe.cafeMood.cafe.domain.cafe.Cafe;
import com.cafe.cafeMood.menu.dto.response.TopMenuResponse;
import com.cafe.cafeMood.tag.dto.TopTagResponse;

import java.util.List;

public record CafeSearchResponse(
        Long cafeId,
        String cafeName,
        String address,
        Double totalScore,
        List<TopTagResponse> moodTags,
        List<TopMenuResponse> coffeeMenus,
        List<TopMenuResponse> dessertMenus
) {

}
