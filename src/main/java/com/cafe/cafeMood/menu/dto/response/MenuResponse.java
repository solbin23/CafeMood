package com.cafe.cafeMood.menu.dto.response;

import com.cafe.cafeMood.menu.domain.Menu;
import com.cafe.cafeMood.menu.domain.MenuCategory;
import com.cafe.cafeMood.menu.domain.MenuStatus;

import java.util.List;

public record MenuResponse(
        Long id,
        Long cafeId,
        String name,
        MenuCategory category,
        Integer price,
        String description,
        String imageUrl,
        Integer displayOrder,
        MenuStatus status,
        List<String> highlights
) {
    public static MenuResponse of(Menu menu) {
        return new MenuResponse(
                menu.getId(),
                menu.getCafeId(),
                menu.getName(),
                menu.getCategory(),
                menu.getPrice(),
                menu.getDescription(),
                menu.getImageUrl(),
                menu.getDisplayOrder(),
                menu.getStatus(),
                menu.getHighlight()
        );
    }
}
