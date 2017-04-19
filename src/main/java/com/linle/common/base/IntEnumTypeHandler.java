package com.linle.common.base;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;


@MappedJdbcTypes(JdbcType.SMALLINT)
public class IntEnumTypeHandler<E extends Enum<E> & IntEnum<E>> extends BaseTypeHandler<E> {

	@SuppressWarnings("unchecked")
	private Class<E> type;
	
	public IntEnumTypeHandler(Class<E> clazz){
		type = clazz;
	}
	
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i,
			E parameter, JdbcType jdbcType) throws SQLException {		
		ps.setInt(i, parameter.getIntValue());
	}

	@Override
	public E getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		return convert(rs.getInt(columnName));
	}

	@Override
	public E getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException {
		return convert(rs.getInt(columnIndex));
	}

	@Override
	public E getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		return convert(cs.getInt(columnIndex));
	}

	private E convert(int status) {
		E[] objs = type.getEnumConstants();
		for (E em : objs) {
			if (em.getIntValue() == status) {
				return em;
			}
		}
		return null;
	}

}
