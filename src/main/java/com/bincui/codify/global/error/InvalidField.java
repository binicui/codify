package com.bincui.codify.global.error;

import com.bincui.codify.global.utils.ExceptionUtils;
import jakarta.validation.ConstraintViolation;
import lombok.Getter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 클라이언트의 잘못된 요청 또는 요청 파라미터로 인해 발생된 에러에 대한 정보를 담는 클래스.
 */

@Getter
public class InvalidField {

    private final String field;

    private final String value;

    private final String reason;

    private InvalidField(final String field, final String value, final String reason) {
        this.field = field;
        this.value = value;
        this.reason = reason;
    }

    /**
     * 단일 필드에 의해 발생된 에러의 정보를 {@link InvalidField} 리스트로 반환한다.
     *
     * @param field     에러가 발생된 필드명
     * @param value     잘못 입력된 값
     * @param reason    에러가 발생된 원인
     * @return          단일 필드 에러에 대한 정보를 담은 리스트
     */
    public static List<InvalidField> of(String field, String value, String reason) {
        List<InvalidField> errors = new ArrayList<>();
        errors.add(new InvalidField(field, value, reason));
        return errors;
    }

    /**
     * {@link BindingResult}의 필드 에러 정보를 {@link InvalidField} 리스트로 변환한다.
     *
     * @param bindingResult 스프링 유효성 검증 결과 객체
     * @return              변환된 {@link InvalidField} 리스트
     */
    public static List<InvalidField> from(BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        return fieldErrors.stream().map(fieldError -> new InvalidField(
                fieldError.getField(),
                ExceptionUtils.safeToString(fieldError.getRejectedValue()),
                fieldError.getDefaultMessage()
        )).collect(Collectors.toList());
    }

    /**
     * 제약 조건 위반으로 인해 발생된 에러의 정보를 {@link InvalidField} 리스트로 변환한다.
     *
     * @param violations    제약 조건 위반 항목들
     * @return              변환된 {@link InvalidField} 리스트
     */
    public static List<InvalidField> from(Set<ConstraintViolation<?>> violations) {
        List<ConstraintViolation<?>> constraintViolations = new ArrayList<>(violations);
        return constraintViolations.stream().map(constraintViolation -> new InvalidField(
                ExceptionUtils.getViolatedField(constraintViolation.getPropertyPath().toString()),
                ExceptionUtils.safeToString(constraintViolation.getInvalidValue()),
                constraintViolation.getMessage()
        )).collect(Collectors.toList());
    }

    /**
     * 잘못된 타입의 요청 파라미터 전달시 발생된 예외에 대한 정보를 {@link InvalidField} 리스트로 변환한다.
     *
     * @param e 메소드 인자 타입 불일치 예외 객체
     * @return  변환된 {@link InvalidField} 리스트
     */
    public static List<InvalidField> from(MethodArgumentTypeMismatchException e) {
        String field = e.getName();
        String requiredType = Objects.requireNonNull(e.getRequiredType()).getSimpleName();
        return of(field, ExceptionUtils.safeToString(e.getValue()), field + "의 타입은 " + requiredType + "이어야 합니다.");
    }
}