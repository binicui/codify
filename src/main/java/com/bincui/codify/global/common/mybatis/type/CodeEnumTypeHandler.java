package com.bincui.codify.global.common.mybatis.type;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * {@link CodeEnum} 인터페이스를 구현한 {@link Enum}과 데이터베이스 컬럼 간의 매핑을 처리하는 타입핸들러.
 *
 * @param <E> 타입핸들러에서 처리할 {@link CodeEnum}의 구현체인 {@link Enum} 타입
 *
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
     * 주어진 코드값과 일치한 값을 가진 {@link Enum}의 상수를 반환한다.
     *
     * @param code  데이터베이스로부터 읽어들인 코드값
     * @return      주어진 코드값과 일치한 경우 {@link Enum} 상수, 그렇지 않은 경우 {@code null} 반환
     */
    private E codeToEnum(String code) {
        for (E constant : enums) {
            if (constant.getCode().equals(code)) {
                return constant;
            }
        }
        return null;
    }
}