package com.hschoi.common.code.mapper;

import lombok.Getter;
import lombok.ToString;

/**
 * <pre>
 * com.hschoi.common.code.mapper_EnumMapperValue.java
 * </pre>
 * @date : 2019. 7. 12.
 * @author : hychoi
 */
@Getter
@ToString
public class EnumValue {
	private String code;
	private String message;

	public EnumValue(EnumType enumMapperType) {
		code = enumMapperType.getCode();
		message = enumMapperType.getMessage();
	}
}
