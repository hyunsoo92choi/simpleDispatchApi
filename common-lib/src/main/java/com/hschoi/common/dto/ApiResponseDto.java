package com.hschoi.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * com.hschoi.common.dto_ApiResponseDto.java
 * </pre>
 * @date : 2019. 7. 12.
 * @author : hychoi
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseDto<T> {
	private int code;
    private String message;
    private T data;
}
