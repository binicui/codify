package com.bincui.codify.global.common.type;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * {@link CodeEnum}을 구현한 {@link Enum} 내의 문자열 타입의 코드값과 데이터베이스 컬럼 간의 매핑을 처리하는 타입 핸들러
 * <p>
 * {@link Enum} 상수의 {@code getCode()} 값을 데이터베이스 컬럼과 매칭하여 저장 또는 읽어오는 등의 작업을 수행하며,
 * 데이터베이스에서 조회한 코드값을 {@link Enum}으로 변환한다.
 * </p>
 * @param <E>   타입 핸들러에서 처리하고자 하는 {@link Enum} 타입
 *
 * @author subin Park
 * @see     CodeEnum
 */

@MappedTypes(CodeEnum.class)
public class CodeEnumTypeHandler<E extends Enum<E>&CodeEnum> extends BaseTypeHandler<E> {

    private final Class<E> type;

    private final E[] constants;

    public CodeEnumTypeHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null!");
        } else {
            this.type = type;
            this.constants = type.getEnumConstants();
            if (!type.isInterface() && this.constants == null) {
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
        return toCodeEnum(rs.getString(columnName));
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return toCodeEnum(rs.getString(columnIndex));
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return toCodeEnum(cs.getString(columnIndex));
    }

    /**
     * 전달된 코드값과 일치한 값을 가진 {@link Enum}의 상수를 반환한다.
     *
     * @param code  데이터베이스로부터 조회해온 코드값
     * @return      코드값과 일치하는 값이 존재할 경우 그 값이 저장된 상수를 반환한다.
     */
    private E toCodeEnum(String code) {
        for (E constant : constants) {
            if (constant.getCode().equals(code)) {
                return constant;
            }
        }
        throw new IllegalArgumentException("Cannot convert '" + code + "' to '" + type.getSimpleName() + "'!");
    }
}