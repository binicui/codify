package com.bincui.codify.global.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum StatusValue {

    YES (1, "Y", true),
    NO (0, "N", false);

    private final int number;

    private final String character;

    private final boolean flag;

    public static boolean toFlag(int value) {
        return Arrays.stream(values())
                .filter(status -> status.getNumber() == value)
                .findFirst()
                .map(StatusValue::isFlag)
                .orElseThrow(() -> new IllegalArgumentException("Cannot convert '" + value + "' to boolean type!"));
    }
}