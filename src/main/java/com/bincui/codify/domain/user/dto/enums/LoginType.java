package com.bincui.codify.domain.user.dto.enums;

import com.bincui.codify.global.common.type.CodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LoginType implements CodeEnum {

    DEFAULT ("LOCAL", "일반 로그인"),
    SOCIAL ("OAUTH", "소셜 로그인");

    private final String code;

    private final String label;
}