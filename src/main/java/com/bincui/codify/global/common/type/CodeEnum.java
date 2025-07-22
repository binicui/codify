package com.bincui.codify.global.common.type;

/**
 * 데이터베이스 컬럼과 매핑되는 코드값을 가진 열거형을 구현하기 위한 인터페이스
 *
 * @see CodeEnumTypeHandler
 */

public interface CodeEnum {
    String getCode();
}