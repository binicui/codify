package com.bincui.codify.global.utils;

/**
 * 예외 처리 및 유효성 검증 시 사용될 보조적인 기능들을 제공하는 유틸리티 클래스
 */

public final class ExceptionUtils {

    /**
     * 주어진 객체를 문자열로 변환한다.
     *
     * @param obj   문자열로 변환하고자 하는 객체
     * @return      전달된 객체의 값이 {@code null}일 경우 빈 문자열을, 그렇지 않은 경우 문자열로 변환하여 반환.
     */
    public static String safeToString(Object obj) {
        return obj == null ? "" : obj.toString();
    }

    /**
     * 제약조건을 위반한 프로퍼티 경로에서 마지막에 위치한 필드명을 추출한다.
     *
     * @param propertyPath  제약조건을 위반한 프로퍼티 경로
     * @return              프로퍼티 경로에서 '.'이 포함된 경우 마지막 필드명을, 그렇지 않은 경우 전체 경로를 반환
     */
    public static String getViolatedField(String propertyPath) {
        int index = propertyPath.lastIndexOf('.');
        return index == -1 ? propertyPath : propertyPath.substring(index + 1);
    }
}