package com.hschoi.homework.common.auth.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * <pre>
 * com.hschoi.homework.common.auth.dto_AuthDto.java
 * </pre>
 * @date : 2019. 7. 12.
 * @author : hychoi
 */
@Getter
@Builder
public class AuthDto {
	private String accessToken;
}
