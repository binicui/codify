package com.bincui.codify.global.common.type;

/**
 * 데이터베이스 컬럼과 매핑되는 {@code code}값을 가진 {@link Enum}을 구현하기 위한 인터페이스
 *
 * @author subin Park
 * @see     CodeEnumTypeHandler
 */

public interface CodeEnum {
    String getCode();
}