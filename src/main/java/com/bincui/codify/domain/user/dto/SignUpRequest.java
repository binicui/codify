package com.bincui.codify.domain.user.dto;

import com.bincui.codify.domain.user.dto.enums.LoginType;
import com.bincui.codify.global.utils.StatusValue;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
public class SignUpRequest {

    private Long id;

    private String email;

    @Setter
    private String password;

    private String confirmPassword;

    private String nickname;

    private String name;

    private String phone;

    @Setter
    private LoginType loginType;

    private final int enabled = StatusValue.YES.getNumber();
}