package com.bincui.codify.global.error;

import com.bincui.codify.global.utils.ExceptionUtils;
import jakarta.validation.ConstraintViolation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
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
 * 클라이언트의 잘못된 요청 파라미터로 인해 발생한 에러의 정보를 처리하고 반환하기 위해 정의한 클래스
 *
 * @author subin Park
 */

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class InvalidField {

    private final String field;

    private final String value;

    private final String reason;

    /**
     * 단일 필드에 의해 발생된 에러에 대한 정보를 리스트로 반환한다.
     *
     * @param field     에러가 발생된 필드명
     * @param value     필드에 담긴 사용자 입력 값
     * @param reason    에러가 발생된 원인
     * @return          에러 정보 리스트
     */
    public static List<InvalidField> of(String field, String value, String reason) {
        List<InvalidField> errors = new ArrayList<>();
        errors.add(new InvalidField(field, value, reason));
        return errors;
    }

    /**
     * 유효성 검증 실패시 발생된 에러에 대한 정보를 리스트로 반환한다.
     *
     * <p>
     * {@code @Valid} 어노테이션을 사용하여 {@code @RequestBody} 또는 {@code @ModelAttribute}로 받아온 파라미터의 유효성 검증을
     * 통과하지 못할 경우 발생되는 에러에 대한 정보를 반환한다.
     * </p>
     *
     * @param bindingResult     유효성 검증 오류에 대한 정보를 담고 있는 객체
     * @return                  에러 정보 리스트
     */
    public static List<InvalidField> from(BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        return fieldErrors.stream().map(fieldError -> new InvalidField(
                fieldError.getField(),
                ExceptionUtils.valueAsString(fieldError.getRejectedValue()),
                fieldError.getDefaultMessage()
        )).collect(Collectors.toList());
    }

    /**
     * 유효성 검증 실패시 발생된 에러에 대한 정보를 리스트로 반환한다.
     *
     * <p>
     * {@code @Validated} 어노테이션을 사용하여 전달된 파라미터를 대상으로 유효성 검증시 통과하지 못할 경우 발생되는 에러에 대한 정보를 반환한다.
     * </p>
     *
     * @param violations    제약 조건 위반으로 발생한 에러 정보를 담고 있는 객체
     * @return              에러 정보 리스트
     */
    public static List<InvalidField> from(Set<ConstraintViolation<?>> violations) {
        List<ConstraintViolation<?>> constraintViolations = new ArrayList<>(violations);
        return constraintViolations.stream().map(constraintViolation -> new InvalidField(
                ExceptionUtils.violatedField(constraintViolation.getPropertyPath().toString()),
                ExceptionUtils.valueAsString(constraintViolation.getInvalidValue()),
                constraintViolation.getMessage()
        )).collect(Collectors.toList());
    }

    /**
     * 전달된 요청 파라미터의 타입 불일치로 인해 발생된 에러에 대한 정보를 반환한다.
     *
     * @param e     요청 파라미터의 타입 불일치 발생되는 예외
     * @return      에러 정보 리스트
     */
    public static List<InvalidField> from(MethodArgumentTypeMismatchException e) {
        String field = e.getName();
        String requiredType = Objects.requireNonNull(e.getRequiredType()).getSimpleName();
        String message = field + "의 타입은 " + requiredType + " 이어야 합니다.";
        return of(field, ExceptionUtils.valueAsString(e.getValue()), message);
    }
}