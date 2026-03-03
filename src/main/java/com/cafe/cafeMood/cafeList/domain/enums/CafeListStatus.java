package com.cafe.cafeMood.cafeList.domain.enums;

public enum CafeListStatus {

    PUBLISHED("공개"), HIDDEN("숨김"), DRAFT("임시저장");

    private final String label;

    CafeListStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
