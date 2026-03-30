package com.cafe.cafeMood.cafe.dto.response;

import com.cafe.cafeMood.cafe.domain.cafe.Cafe;
import com.cafe.cafeMood.menu.dto.response.MenuResponse;
import com.cafe.cafeMood.review.dto.response.CafeReviewResponse;
import com.cafe.cafeMood.tag.dto.TopTagResponse;

import java.util.List;
import java.util.Map;

public record CafeDetailResponse(
        Long cafeId,
        String cafeName,
        String address,
        String phone,
        String description,
        Map<String, List<MenuResponse>> menus,
        List<TopTagResponse> moodTags,
        List<CafeReviewResponse> reviews
        ) {

}
