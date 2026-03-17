package com.cafe.cafeMood.cafe.domain.cafe;

public enum CafeStatus {
    DRAFT,// 임시저장
    PENDING, //승인대기
    PUBLISHED, //게시중
    HIDDEN, //관리자 및 점주에 의해 숨김
    SUSPENDED,// 제재
    DELETED; // 삭제

    public boolean isDeleted() {
        return this == DELETED;
    }

    public boolean isPublish() {
        return this == PUBLISHED;
    }

    public boolean canOwnerEdit() {
        return this == DRAFT || this == HIDDEN;
    }

    public boolean canRequestApproval(){
        return this == DRAFT || this == HIDDEN;
    }

    public boolean canPublish(){
        return this == PENDING || this == HIDDEN;
    }
}
