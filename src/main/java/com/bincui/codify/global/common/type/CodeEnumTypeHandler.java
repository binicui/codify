package com.bincui.codify.global.common.type;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * {@link CodeEnum}을 구현한 열거형을 대상으로 데이터베이스 컬럼 간의 매핑을 처리하는 타입 핸들러.
 *
 * @param <E> {@link CodeEnumTypeHandler}에서 처리할 열거형
 * @see CodeEnum
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
            if (this.enums == null) {
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
        return codeToEnum(rs.getString(columnName));
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return codeToEnum(rs.getString(columnIndex));
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return codeToEnum(cs.getString(columnIndex));
    }

    /**
     * 주어진 코드값과 일치한 값을 가진 열거형 상수를 반환한다.
     *
     * @param code  데이터베이스로부터 읽어들인 코드값
     * @return      코드값과 일치할 경우 열거형 상수, 없을 경우 {@code null} 반환
     */
    private E codeToEnum(String code) {
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