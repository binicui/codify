package com.bincui.codify.global.common.response;

import com.bincui.codify.global.common.response.base.GenericResponse;
import com.bincui.codify.global.common.response.base.ResponseEnum;
import lombok.Getter;

/**
 * 요청 처리 결과가 정상적으로 처리되었을 경우 반환될 응답 바디의 구조를 정의한 클래스
 *
 * @param <T> 성공 응답 시 반환될 결과 데이터의 타입
 */

@Getter
public class ApiResponse<T> extends GenericResponse {

    private final T data;

    private ApiResponse(int code, String message, T data) {
        super(code, message);
        this.data = data;
    }

    /**
     * 성공 응답을 반환한다.
     *
     * @param data  응답 결과 데이터
     * @return      응답 객체
     * @param <T>   응답 결과 데이터 타입
     */
    public static<T> ApiResponse<T> ok(final T data) {
        return new ApiResponse<>(ResponseEnum.OK.getCode(), ResponseEnum.OK.getMessage(), data);
    }
}