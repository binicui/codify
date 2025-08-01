package com.bincui.codify.global.common.response.base;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 성공 및 에러 응답의 본문을 구성하는데 사용될 응답 코드와 메시지, HTTP 상태 코드 등을 정의한 {@link Enum}
 *
 * @author subin Park
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
    INVALID_PARAMETER (HttpStatus.BAD_REQUEST, -800101, "요청 파라미터의 값이 유효하지 않습니다."),
    INVALID_PARAMETER_TYPE (HttpStatus.BAD_REQUEST, -800102, "유효하지 않은 타입의 요청 파라미터입니다."),
    MISSING_PARAMETER (HttpStatus.BAD_REQUEST, -800103, "필수 요청 파라미터가 누락되었습니다."),
    UNSUPPORTED_HTTP_METHOD (HttpStatus.METHOD_NOT_ALLOWED, -805100, "지원하지 않는 HTTP 메소드 요청입니다."),
    INTERNAL_SERVER_ERROR (HttpStatus.INTERNAL_SERVER_ERROR, -999999, "내부 서버에 오류가 발생했습니다.")
    ;

    private final HttpStatus status;

    private final int code;

    private final String message;
}