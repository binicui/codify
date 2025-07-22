package com.bincui.codify.global.common.response.base;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 요청 처리 후 반환될 응답을 구성할 성공 및 에러 응답 코드와 관련 정보를 정의한 열거형.
 */

@RequiredArgsConstructor
@Getter
public enum ResponseEnum {

    /**
     * 성공 응답 코드
     */
    /* 공통 */
    OK (100000, HttpStatus.OK, "요청이 정상적으로 처리되었습니다."),

    /**
     * 공통 에러 응답 코드
     */
    /* 공통 */
    INVALID_REQUEST_PARAM_VALUE (-800101, HttpStatus.BAD_REQUEST, "요청 파라미터의 값이 유효하지 않습니다."),
    INVALID_REQUEST_PARAM_TYPE (-800102, HttpStatus.BAD_REQUEST, "유효하지 않은 타입의 요청 파라미터입니다."),
    NOT_SUPPORTED_METHOD (-805100, HttpStatus.METHOD_NOT_ALLOWED, "지원하지 않는 HTTP 메소드 요청입니다."),
    UNKNOWN_INTERNAL_SERVER_ERROR (-999999, HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버에 에러가 발생했습니다."),

    /* 사용자 */
    USER_NOT_FOUND (-804210, HttpStatus.NOT_FOUND, "요청하신 사용자 계정을 찾을 수 없습니다."),
    LOGIN_REQUIRED (-801310, HttpStatus.UNAUTHORIZED, "로그인이 필요한 서비스입니다."),
    ACCESS_DENIED (-803310, HttpStatus.FORBIDDEN, "요청 자원에 대한 권한이 없습니다.")
    ;

    private final int code;

    private final HttpStatus status;

    private final String message;
}