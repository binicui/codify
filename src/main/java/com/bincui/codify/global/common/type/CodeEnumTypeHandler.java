package com.bincui.codify.global.common.type;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * {@link CodeEnum}을 구현한 열거형을 대상으로 데이터베이스 컬럼 간의 매핑을 처리하는 타입 핸들러로,
 * 열거형으로 정의된 코드값을 데이터베이스에 저장하거나 데이터베이스의 컬럼 값을 열거형으로 변환한다.
 */

@MappedTypes(CodeEnum.class)
public class CodeEnumTypeHandler<E extends Enum<E> & CodeEnum> extends BaseTypeHandler<E> {

    private final Class<E> type;

    private final E[] enums;

    public CodeEnumTypeHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null!");
        }
        this.type = type;
        this.enums = type.getEnumConstants();
        if (enums == null) {
            throw new IllegalArgumentException("'" + type.getSimpleName() + "' does not represent an enum type!");
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getCode());
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return fromCode(rs.getString(columnName));
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return fromCode(rs.getString(columnIndex));
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return fromCode(cs.getString(columnIndex));
    }

    /**
     * 데이터베이스의 컬럼 값과 일치한 상수값을 정의한 열거형을 반환한다.
     *
     * @param code  데이터베이스로부터 가져온 코드값
     * @return      데이터베이스 컬럼 값과 일치한 상수값이 존재할 경우, 해당 상수값을 정의한 열거형을, 존재하지 않을 경우 {@code null} 반환.
     */
    private E fromCode(String code) {
        try {
            for (E codeEnum : enums) {
                if (codeEnum.getCode().equals(code)) {
                    return codeEnum;
                }
            }
            return null;
        } catch (Exception e) {
            throw new IllegalArgumentException("Cannot convert '" + code + "' to '" + type.getSimpleName() + "'!", e);
        }
    }
}