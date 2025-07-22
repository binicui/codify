package com.bincui.codify.global.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 전역적으로 사용되는 상태값들을 코드 단에서 일관되게 처리하도록 서로 다른 타입의 값들을 의미별로 정의한 열거형.
 */

@RequiredArgsConstructor
@Getter
public enum StatusValues {

    YES (1, "Y", true),
    NO (0, "N", false);

    private final int number;

    private final String character;

    private final boolean flag;
}