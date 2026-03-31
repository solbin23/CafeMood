package com.cafe.cafeMood.tag.dto;

public record TopTagResponse(
        Long tagId,
        String tagName,
        double score
) {
}
