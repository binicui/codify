package com.bincui.codify.global.common.type.handler;

import com.bincui.codify.global.common.type.CodeEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 */

@MappedTypes(CodeEnum.class)
public class CodeEnumTypeHandler<E extends Enum<E> & CodeEnum> extends BaseTypeHandler<E> {

    private final Class<E> type;
    private final E[] enums;

    public CodeEnumTypeHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null!");
        } else {
            this.type = type;
            this.enums = type.getEnumConstants();
            if (enums == null) {
                throw new IllegalArgumentException("'" + type.getSimpleName() + "' does not represent an enum type!");
            }
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getCode());
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return codeOf(rs.getString(columnName));
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return codeOf(rs.getString(columnIndex));
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return codeOf(cs.getString(columnIndex));
    }

    private E codeOf(String code) {
        try {
            for (E constant : enums) {
                if (constant.getCode().equals(code)) {
                    return constant;
                }
            }
            return null;
        } catch (Exception e) {
            throw new IllegalArgumentException("Cannot convert '" + code + "' to '" + type.getSimpleName() + "'!", e);
        }

    }
}