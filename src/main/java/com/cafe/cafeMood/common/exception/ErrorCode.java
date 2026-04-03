package com.cafe.cafeMood.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    CAFE_NOT_FOUND(HttpStatus.NOT_FOUND,"CAFE-404","카페를 찾을 수 없습니다."),
    INVALID_CAFE_STATE(HttpStatus.BAD_REQUEST,"CAFE-400","현재 상태에서는 처리할 수 없습니다."),
    CAFE_ALREADY_DELETED(HttpStatus.BAD_REQUEST,"CAFE-400","이미 삭제된 카페입니다."),

    MENU_NOT_FOUND(HttpStatus.NOT_FOUND,"MENU-404","메뉴를 찾을 수 없습니다."),
    MENU_DELETED(HttpStatus.BAD_REQUEST,"MENU-400","이미 삭제된 메뉴입니다."),
    INVALID_MENU_STATE(HttpStatus.BAD_REQUEST,"MENU-400","현재 상태에서는 처리할 수 없습니다."),

    DUPLICATED_LOGIN_ID(HttpStatus.BAD_REQUEST, "USER-400", "이미 사용 중인 아이디입니다."),
    DUPLICATED_EMAIL(HttpStatus.BAD_REQUEST, "USER-400", "이미 사용 중인 이메일입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER-404", "사용자를 찾을 수 없습니다."),

    DUPLICATED_BUSINESS_NUMBER(HttpStatus.BAD_REQUEST,"OWNER-400","이미 사용중인 사업자등록번호 입니다."),
    OWNER_NOT_FOUND(HttpStatus.NOT_FOUND,"OWNER-404", "오너 계정을 찾을 수 없습니다."),

    TAG_NOT_FOUND(HttpStatus.NOT_FOUND,"TAG-404","태그를 찾을 수 없습니다."),
    INVALID_TAG(HttpStatus.BAD_REQUEST,"TAG-400","비활성화된 태그는 선택할 수 없습니다."),
    DUPLICATE_TAG_SELECTION(HttpStatus.BAD_REQUEST, "TAG-400","중복된 태그는 선택할 수 없습니다."),

    ALREADY_REVIEWED_CAFE(HttpStatus.BAD_REQUEST,"REVIEW-400","이미 리뷰 작성한 카페입니다."),
    NOT_FOUND_REVIEW(HttpStatus.NOT_FOUND,"REVIEW-404","리뷰를 찾을 수 없습니다."),

    FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON-403", "접근 권한이 없습니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "LOGIN-400","비밀번호가 일치하지 않습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"AUTH-401", "인증이 필요합니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"SERVER-500","서버 오류가 발생했습니다.")
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
