package com.cafe.cafeMood.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    CAFE_NOT_FOUND(HttpStatus.NOT_FOUND,"CAFE-404","카페를 찾을 수 없습니다."),
    CAFE_ALREADY_DELETED(HttpStatus.BAD_REQUEST,"CAFE-400","이미 삭제된 카페입니다."),
    INVALID_CAFE_STATE(HttpStatus.BAD_REQUEST,"CAFE-400","현재 상태에서는 처리할 수 없습니다."),
    MENU_NOT_FOUND(HttpStatus.NOT_FOUND,"MENU-404","메뉴를 찾을 수 없습니다."),
    MENU_DELETED(HttpStatus.BAD_REQUEST,"MENU-400","이미 삭제된 메뉴입니다."),
    INVALID_MENU_STATE(HttpStatus.BAD_REQUEST,"MENU-400","현재 상태에서는 처리할 수 없습니다."),

    TAG_NOT_FOUND(HttpStatus.NOT_FOUND,"TAG-404","태그를 찾을 수 없습니다."),
    NOT_FOUND_REVIEW(HttpStatus.NOT_FOUND,"REVIEW-404","리뷰를 찾을 수 없습니다."),
    DUPLICATED_LOGIN_ID(HttpStatus.BAD_REQUEST, "USER-400", "이미 사용 중인 아이디입니다."),
    DUPLICATED_EMAIL(HttpStatus.BAD_REQUEST, "USER-400", "이미 사용 중인 이메일입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER-404", "사용자를 찾을 수 없습니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON-403", "접근 권한이 없습니다.")
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
