package com.bincui.codify.global.common.response;

import com.bincui.codify.global.common.response.base.GenericResponse;
import com.bincui.codify.global.common.response.base.ResponseEnum;
import lombok.Getter;

/**
 * 성공 응답 클래스
 *
 * @param <T> 성공 응답 본문에 담길 결과 데이터의 타입
 */

@Getter
public class ApiResponse<T> extends GenericResponse {

    /**
     * 클라이언트가 요청한 실제 데이터로, {@code null}일 경우 빈 객체 또는 빈 컬렉션을 담는다.
     */
    private final T data;

    private ApiResponse(final int code, final String message, final T data) {
        super(code, message);
        this.data = data;
    }

    /**
     * 성공 응답을 반환한다.
     *
     * @param data  결과 데이터
     * @return      응답 객체
     * @param <T>   결과 데이터의 타입
     */
    public static<T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(ResponseEnum.OK.getCode(), ResponseEnum.OK.getMessage(), data);
    }
}