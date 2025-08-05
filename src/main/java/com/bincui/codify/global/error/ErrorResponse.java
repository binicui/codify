package com.bincui.codify.global.error;

import com.bincui.codify.global.common.response.base.GeneralResponse;
import com.bincui.codify.global.common.response.base.ResponseEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

/**
 * 클라이언트 요청 처리 결과에 대한 실패 또는 서버 에러 응답을 반환하기 위해 정의한 클래스
 */

@Getter
public class ErrorResponse extends GeneralResponse {

    /**
     * 실패 응답 본문에 포함될 에러 정보 리스트로 {@code null}일 경우 빈 리스트로 처리된다.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final List<InvalidField> errors;

    @Builder
    private ErrorResponse(int code, String message, List<InvalidField> errors) {
        super(code, message);
        this.errors = errors;
    }

    /**
     * 클라이언트의 잘못된 요청으로 인해 발생된 에러에 대한 실패 응답을 반환한다.
     * 
     * @param responseEnum  응답 코드와 메시지 등을 정의한 {@link Enum}
     * @param errors        에러 정보
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
     * 요청 처리 중 서버에서 발생된 에러에 대한 응답을 반환한다.
     * 
     * @param responseEnum  응답 코드와 메시지 등을 정의한 {@link Enum}
     * @return              응답 객체
     */
    public static ErrorResponse error(ResponseEnum responseEnum) {
        return ErrorResponse.builder()
                .code(responseEnum.getCode())
                .message(responseEnum.getMessage())
                .build();
    }
}