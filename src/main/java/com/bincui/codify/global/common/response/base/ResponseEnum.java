package com.bincui.codify.global.common.response.base;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 요청 처리 후 클라이언트에게 반환될 응답 구성시 사용될 성공 및 에러 응답 코드와 관련 정보들을 정의한 {@link Enum}.
 */

@RequiredArgsConstructor
@Getter
public enum ResponseEnum {

    /**
     * 성공 응답 코드
     */
    /* 공통 */
    OK (HttpStatus.OK, 100000, "요청이 정상적으로 처리되었습니다."),

    /**
     * 에러 응답 코드
     */
    /* 공통 */
    INVALID_PARAMETER_VALUE (HttpStatus.BAD_REQUEST, -800101, "요청 파라미터의 값이 유효하지 않습니다."),
    INVALID_PARAMETER_TYPE (HttpStatus.BAD_REQUEST, -800102, "유효하지 않은 타입의 요청 파라미터입니다."),
    UNSUPPORTED_HTTP_METHOD (HttpStatus.METHOD_NOT_ALLOWED, -805100, "지원하지 않는 HTTP 메소드 요청입니다."),
    INTERNAL_SERVER_ERROR (HttpStatus.INTERNAL_SERVER_ERROR, -999999, "내부 서버에 오류가 발생했습니다."),

    /* 사용자 */
    USER_NOT_FOUND (HttpStatus.NOT_FOUND, -804210, "요청하신 사용자를 찾을 수 없습니다."),
    LOGIN_REQUIRED (HttpStatus.UNAUTHORIZED, -801310, "로그인이 필요한 서비스입니다."),
    ACCESS_DENIED (HttpStatus.FORBIDDEN, -803310, "요청된 자원에 대한 권한이 없습니다.")
    ;

    private final HttpStatus status;

    private final int code;

    private final String message;
}