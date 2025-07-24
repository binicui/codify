package com.bincui.codify.global.common.response.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 성공 및 에러 응답의 공통 필드를 정의한 추상 클래스
 */

@Getter
public abstract class GenericResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime responseAt;

    private final int code;

    private final String message;

    public GenericResponse(final int code, final String message) {
        this.responseAt = LocalDateTime.now();
        this.code = code;
        this.message = message;
    }
}