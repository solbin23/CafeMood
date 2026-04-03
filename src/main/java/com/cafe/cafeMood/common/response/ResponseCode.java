package com.cafe.cafeMood.common.response;

import org.springframework.http.HttpStatus;

public enum ResponseCode {
    SUCCESS(HttpStatus.OK,"SUCCESS","성공"),
    DELETED(HttpStatus.OK,"SUCCESS","삭제"),
    CREATED(HttpStatus.CREATED,"CREATED","생성되었습니다."),
    OWNER_CREATED(HttpStatus.CREATED,"OWNER_CREATED","오너 계정이 성공적으로 생성되었습니다."),
    USER_CREATED(HttpStatus.CREATED,"USER_CREATED","유저 계정이 성공적으로 생성되었습니다."),
    CAFE_NOT_FOUND(HttpStatus.NOT_FOUND,"CAFE_NOT_FOUND","존재하지 않는 카페입니다."),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST,"INVALID_REQUEST","잘못된 요청입니다.");


    private final HttpStatus status;
    private final String code;
    private final String message;

    ResponseCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public HttpStatus status() {
        return status;
    }
    public String code() {
        return code;
    }
    public String message() {
        return message;
    }
}
