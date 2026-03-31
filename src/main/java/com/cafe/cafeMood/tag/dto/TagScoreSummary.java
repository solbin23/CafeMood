package com.cafe.cafeMood.tag.dto;

public record TagScoreSummary(
        Long cafeId,
        Long tagId,
        String tagName,
        double score

) {
}
