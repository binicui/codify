package com.bincui.codify.global.common.response;

import com.bincui.codify.global.common.response.base.GenericResponse;
import com.bincui.codify.global.common.response.base.ResponseEnum;
import lombok.Getter;

/**
 * 요청이 정상적으로 처리될 경우 반환되는 성공 응답을 정의한 클래스
 *
 * @param <T>   성공 응답 구성시 포함될 결과 데이터의 타입
 */

@Getter
public class ApiResponse<T> extends GenericResponse {

    /**
     * 응답 결과 데이터로서, {@code null}일 경우 빈 객체 또는 빈 컬렉션으로 대체하여 할당한다.
     */
    private final T data;

    private ApiResponse(int code, String message, T data) {
        super(code, message);
        this.data = data;
    }

    /**
     * 성공 응답을 반환한다.
     *
     * @param data  응답 결과 데이터
     * @return      성공 응답 객체
     * @param <T>   응답 결과 데이터의 타입
     */
    public static<T> ApiResponse<T> ok(final T data) {
        return new ApiResponse<>(ResponseEnum.OK.getCode(), ResponseEnum.OK.getMessage(), data);
    }
}