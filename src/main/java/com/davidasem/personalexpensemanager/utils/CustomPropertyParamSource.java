package com.davidasem.personalexpensemanager.utils;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;

import java.sql.Types;

public class CustomPropertyParamSource extends BeanPropertySqlParameterSource {

		public CustomPropertyParamSource(Object object) {
				super(object);
		}


		@Override
		public int getSqlType(String paramName) {
				int sqlType = super.getSqlType(paramName);
				return (sqlType == TYPE_UNKNOWN && hasValue(paramName) &&
						getValue(paramName) != null && getValue(paramName).getClass().isEnum()) ?
						Types.VARCHAR : sqlType;
		}

}
