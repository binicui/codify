package com.bincui.codify.global.common.response.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 요청 처리 후 반환될 응답 바디부를 구성할 때 사용될 코드, 메시지, HTTP 상태코드를 정의한 열거형.
 */

@AllArgsConstructor
@Getter
public enum ResponseEnum {

    OK (100000, "요청이 정상적으로 처리되었습니다.", HttpStatus.OK),

    INVALID_REQUEST_VALUE (-800010, "요청 파라미터의 값이 유효하지 않습니다.", HttpStatus.BAD_REQUEST),
    INVALID_REQUEST_PARAM_TYPE (-800011, "유효하지 않은 타입의 요청 파라미터입니다.", HttpStatus.BAD_REQUEST),
    NOT_SUPPORTED_METHOD (-805020, "지원하지 않는 메소드 요청입니다.", HttpStatus.METHOD_NOT_ALLOWED),
    INTERNAL_SERVER_ERROR (-900000, "내부 서버에 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),

    USER_NOT_FOUND (-804120, "요청하신 사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    LOGIN_REQUIRED (-801130, "로그인이 필요한 서비스입니다.", HttpStatus.UNAUTHORIZED),
    ACCESS_DENIED (-803130, "접근 권한이 없습니다.", HttpStatus.FORBIDDEN)
    ;

    private final int code;

    private final String message;

    private final HttpStatus status;
}