package com.hschoi.common.exception;

import com.hschoi.common.code.HttpStatusType;

import lombok.Getter;

/**
 * <pre>
 * com.hschoi.common.exception_CustomException.java
 * </pre>
 * @date : 2019. 7. 12.
 * @author : hychoi
 */
@Getter
public class CustomException extends RuntimeException {
	
	/**
	 * <pre>
	 * com.hschoi.common.exception_CustomException.java
	 * </pre>
	 * @date : 2019. 7. 13.
	 * @author : hychoi
	 */
	private static final long serialVersionUID = 8098638552937086314L;
	
	private HttpStatusType resultCode;

    public CustomException(HttpStatusType resultCode) {
        super(resultCode.getMessage());        
        this.resultCode = resultCode;
    }

    public CustomException(HttpStatusType resultCode, String additionalString) {
        super(resultCode.getMessage() + additionalString);
        this.resultCode = resultCode;
    }
}
