package com.bincui.codify.global.error;

import com.bincui.codify.global.common.response.base.GenericResponse;
import com.bincui.codify.global.common.response.base.ResponseEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

/**
 * 실패 및 에러 응답 클래스
 */

@Getter
public class ErrorResponse extends GenericResponse {

    /**
     * 실패 응답 반환시 응답 본문에 포함될 에러에 대한 정보를 담는 리스트로, {@code null}일 경우 빈 리스트 객체를 담는다.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final List<InvalidField> errors;

    @Builder
    private ErrorResponse(final int code, final String message, final List<InvalidField> errors) {
        super(code, message);
        this.errors = errors;
    }

    /**
     * 사용자의 잘못된 요청 또는 요청 파라미터로 인한 에러에 대한 실패 응답을 반환한다.
     * 
     * @param responseEnum  응답 코드 및 관련 정보들을 정의한 {@link Enum}
     * @param errors        발생된 에러에 대한 정보
     * @return              응답 객체
     */
    public static ErrorResponse failure(ResponseEnum responseEnum, List<InvalidField> errors) {
        return ErrorResponse.builder()
                .code(responseEnum.getCode())
                .message(responseEnum.getMessage())
                .errors(errors == null ? Collections.emptyList() : errors)
                .build();
    }

    /**
     * 서버 측에서 발생된 에러에 대한 에러 응답을 반환한다.
     * 
     * @param responseEnum  응답 코드 및 관련 정보들을 정의한 {@link Enum}
     * @return              응답 객체
     */
    public static ErrorResponse error(ResponseEnum responseEnum) {
        return ErrorResponse.builder()
                .code(responseEnum.getCode())
                .message(responseEnum.getMessage())
                .build();
    }
}