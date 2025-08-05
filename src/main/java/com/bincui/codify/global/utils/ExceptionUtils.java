package com.bincui.codify.global.utils;

/**
 * 예외 처리 및 유효성 검증 시 사용될 보조적인 기능들을 제공하는 유틸리티 클래스
 *
 * @author subin Park
 */

public final class ExceptionUtils {

    private ExceptionUtils() {}

    /**
     * 주어진 객체를 문자열로 변환한다.
     *
     * @param obj   문자열로 변환할 객체
     * @return      주어진 값이 {@code null}일 경우 빈 문자열, 아닐 경우 변환된 문자열을 반환
     */
    public static String valueAsString(Object obj) {
        return obj == null ? "" : obj.toString();
    }

    /**
     * 제약 조건이 위반된 프로퍼티 경로에서 마지막에 위치한 필드명을 추출한다.
     *
     * @param path  제약 조건 위반이 발생한 프로퍼티 경로
     * @return      프로퍼티 경로가 점('.')으로 구분된 경우 마지막에 위치한 필드명을, 그렇지 않은 경우 전체 경로 반환
     */
    public static String violatedField(String path) {
        int index = path.lastIndexOf('.');
        return index == -1 ? path : path.substring(index + 1);
    }
}