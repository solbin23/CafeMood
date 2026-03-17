package com.cafe.cafeMood.menu.dto.request;

import com.cafe.cafeMood.menu.domain.MenuCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record MenuCreateRequest(
        @NotNull
        Long cafeId,
        @NotBlank
        @Size(max = 100)
        String name,
        @NotNull
        MenuCategory category,
        @NotNull
        Integer price,
        @Size(max = 500)
        String description,
        @Size(max = 500)
        String imageUrl,
        @NotNull
        Integer displayOrder,
        List<String> highlights
) {
}
