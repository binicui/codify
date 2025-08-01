package com.bincui.codify.global.common.response;

import com.bincui.codify.global.common.response.base.GeneralResponse;
import com.bincui.codify.global.common.response.base.ResponseEnum;
import lombok.Getter;

/**
 * 클라이언트의 요청에 따른 성공 응답을 처리하기 위해 정의한 클래스
 *
 * @param <T> 응답 결과 데이터의 타입
 *
 * @author subin Park
 * @see ApiResponseBodyAdvice
 */

@Getter
public class ApiResponse<T> extends GeneralResponse {

    /**
     * API 요청에 따른 응답 결과 데이터로서, {@code null}일 경우 빈 객체 또는 빈 리스트가 반환된다.
     */
    private final T data;

    private ApiResponse(int code, String message, T data) {
        super(code, message);
        this.data = data;
    }

    /**
     * 성공 응답을 반환한다.
     *
     * @param data  요청에 따른 응답 결과 데이터
     * @return      응답 객체
     * @param <T>   응답 결과 데이터의 타입
     */
    public static<T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(ResponseEnum.OK.getCode(), ResponseEnum.OK.getMessage(), data);
    }
}