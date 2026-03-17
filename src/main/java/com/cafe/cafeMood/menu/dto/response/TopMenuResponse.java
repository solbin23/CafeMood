package com.cafe.cafeMood.menu.dto.response;

import com.cafe.cafeMood.menu.domain.Menu;


import java.util.List;

public record TopMenuResponse(
        Long id,
        String name,
        Integer price,
        String imageUrl,
        List<String> highlights

) {
    public static TopMenuResponse from(Menu menu) {
        return new TopMenuResponse(
                menu.getId(),
                menu.getName(),
                menu.getPrice(),
                menu.getImageUrl(),
                menu.getHighlight()
        );
    }
}
