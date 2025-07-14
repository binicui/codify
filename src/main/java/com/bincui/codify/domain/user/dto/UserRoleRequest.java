package com.bincui.codify.domain.user.dto;

import com.bincui.codify.domain.user.dto.enums.RoleType;
import com.bincui.codify.global.utils.StatusValue;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserRoleRequest {

    private Long userId;

    private RoleType roleType;

    private String usedYn;

    public UserRoleRequest(Long userId, RoleType roleType, StatusValue status) {
        this.userId = userId;
        this.roleType = roleType;
        this.usedYn = status.getCharacter();
    }
}