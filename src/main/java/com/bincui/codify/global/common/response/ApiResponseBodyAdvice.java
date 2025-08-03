package com.bincui.codify.global.common.response;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

/**
 * 클라이언트 요청에 대한 성공 응답 반환시, 응답 결과 데이터({@code data})의 데이터가 {@code null}일 경우, 빈 객체 또는 빈 리스트를
 * 담아 반환 처리를 수행하는 클래스
 *
 * <p>
 * {@code @RestController}가 선언된 컨트롤러 클래스 내부 메소드들의 반환타입이 {@link ResponseEntity}인 경우 적용된다.
 * </p>
 *
 * @see ApiResponse
 * @author  subin Park
 */

@RestControllerAdvice(basePackages = "com.bincui.codify.domain")
public class ApiResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    /**
     * 컨트롤러 메소드의 반환 타입이 {@link ResponseEntity}이거나 그 하위 타입인지 판단하여 {@code beforeBodyWrite()}를 호출한다.
     *
     * @param returnType    컨트롤러 메소드의 반환 타입 정보
     * @param converterType HTTP 메시지 컨버터 타입
     * @return              반환 타입이 {@link ResponseEntity}일 경우 {@code true}, 아닐 경우 {@code false} 반환
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return ResponseEntity.class.isAssignableFrom(returnType.getParameterType());
    }

    /**
     * {@code supports()}의 결과가 {@code true}일 경우, {@link HttpMessageConverter}가 실행되기 전 응답 데이터의 수정 처리를 수행한다.
     * <p>
     * 응답 결과 데이터가 {@code null}일 경우, 그 데이터의 유형에 따라 빈 객체 또는 빈 리스트 객체를 {@link ApiResponse}에 담아 반환한다.
     * </p>
     *
     * @param body                  컨트롤러에서 반환할 실제 응답 결과 데이터
     * @param returnType            컨트롤러 메소드의 반환 타입 정보
     * @param selectedContentType   HTTP 요청의 Content-Type
     * @param selectedConverterType 선택된 메시지 컨버터 타입
     * @param request               HTTP 요청 객체
     * @param response              HTTP 응답 객체
     * @return                      {@link ApiResponse} 객체
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                                  ServerHttpResponse response) {
        Type methodReturnType = returnType.getGenericParameterType();
        Type actualType = (methodReturnType instanceof ParameterizedType)
                        ? ((ParameterizedType) methodReturnType).getActualTypeArguments()[0]
                        : methodReturnType;
        Object payload = (body == null) ? generateEmptyPayload(actualType) : body;
        return ApiResponse.ok(payload);
    }

    /**
     * 컨트롤러 메소드에서 반환될 성공 응답의 결과 데이터 타입에 따라 빈 {@link java.util.Map} 또는 빈 {@link List}를 반환한다.
     *
     * @param actualType    {@link ResponseEntity}가 감싸고 있는 실제 데이터 타입
     * @return              실제 데이터 타입이 {@link List}일 경우 빈 {@link List}, 아닐 경우 빈 {@link java.util.Map}을 반환
     */
    private Object generateEmptyPayload(Type actualType) {
        final Type rawType = (actualType instanceof ParameterizedType)
                            ? ((ParameterizedType) actualType).getRawType()
                            : actualType;
        if (rawType instanceof Class<?>) {
            final Class<?> clazz = (Class<?>) rawType;
            if (List.class.isAssignableFrom(clazz)) {
                return Collections.emptyList();
            }
        }
        return Collections.emptyMap();
    }
}