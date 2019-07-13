package com.hschoi.common.code;

import java.util.Arrays;

import com.hschoi.common.code.mapper.EnumType;
import com.hschoi.common.exception.CustomException;
import static com.hschoi.common.code.HttpStatusType.INVALID_USERTYPE;

/**
 * <pre>
 * com.hschoi.common.code_UserType.java
 * </pre>
 * @date : 2019. 7. 12.
 * @author : hychoi
 */
public enum UserType implements EnumType {
	DRIVER("기사")
  , PASSENGER("승객")
  , DRIVERer("기사")
  ;

	private String message;

	UserType(String message) {
		this.message = message;
	}

	@Override
	public String getCode() {
		// TODO Auto-generated method stub
		return name();
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return message;
	}
	
	public static UserType of(String str) {
        return Arrays.stream(UserType.values())
            .filter(e -> e.name().equals(str.toUpperCase()))
            .findFirst()
            .orElseThrow(() -> new CustomException(INVALID_USERTYPE));
    }
}
