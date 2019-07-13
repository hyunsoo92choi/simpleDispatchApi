package com.hschoi.common.code;

import static com.hschoi.common.code.HttpStatusType.INVALID_CALLSTATUSTYPE;

import java.util.Arrays;

import com.hschoi.common.code.mapper.EnumType;
import com.hschoi.common.exception.CustomException;

/**
 * <pre>
 * com.hschoi.common.code_CallStatusType.java
 * </pre>
 * @date : 2019. 7. 12.
 * @author : hychoi
 */
public enum CallStatusType implements EnumType {
	
	REQUESTED("요청중")
  , ASSIGNED("배차완료")
  , COMPLETED("완료")
  , CANCELED("취소");

	private String message;

	CallStatusType(String message) {
		this.message = message;
	}

	@Override
	public String getCode() {
		return name();
	}

	@Override
	public String getMessage() {
		return message;
	}

	public static CallStatusType of(String str) {
		
		return Arrays.stream(CallStatusType.values())
					 .filter(e -> e.name().equals(str.toUpperCase()))
					 .findFirst()
					 .orElseThrow(() -> new CustomException(INVALID_CALLSTATUSTYPE));
	}
}
