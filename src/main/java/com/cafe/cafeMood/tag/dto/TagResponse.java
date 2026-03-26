package com.cafe.cafeMood.tag.dto;

import com.cafe.cafeMood.tag.domain.Tag;
import com.cafe.cafeMood.tag.domain.TagCategory;

public record TagResponse(Long tagId,
                          String name,
                          TagCategory tagCategory) {

    public static TagResponse from(Tag tag) {
        return new TagResponse(tag.getId(), tag.getName(), tag.getCategory());
    }
}
