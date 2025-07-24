package com.bincui.codify.global.common.mybatis.type;

/**
 * 데이터베이스 컬럼과 매핑되는 코드값을 가진 열거형을 구현하기 위한 인터페이스
 *
 * @see CodeEnumTypeHandler
 */

public interface CodeEnum {

    /**
     * {@link Enum} 상수가 가지는 문자열 타입의 코드값을 반환한다.
     */
    String getCode();
}