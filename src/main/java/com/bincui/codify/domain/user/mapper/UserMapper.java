package com.bincui.codify.domain.user.mapper;

import com.bincui.codify.domain.user.dto.SignUpRequest;
import com.bincui.codify.domain.user.dto.UserRoleRequest;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    void insertUser(SignUpRequest signUpDto);

    int insertUserRole(UserRoleRequest userRoleDto);
}