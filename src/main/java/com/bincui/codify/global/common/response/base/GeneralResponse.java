package com.bincui.codify.global.common.response.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 성공과 에러 응답 본문에 공통적으로 포함될 필드들을 정의한 추상 클래스
 *
 * @author subin Park
 */

@Getter
public abstract class GeneralResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime responseAt;

    private final int code;

    private final String message;

    public GeneralResponse(int code, String message) {
        this.responseAt = LocalDateTime.now();
        this.code = code;
        this.message = message;
    }
}