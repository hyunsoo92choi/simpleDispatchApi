package com.hschoi.common.config;

import java.lang.annotation.Annotation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.hschoi.common.code.HttpStatusType;
import com.hschoi.common.dto.ApiErrorResponseDto;
import com.hschoi.common.dto.ApiResponseDto;
import com.hschoi.common.exception.CustomException;

/**
 * <pre>
 * com.hschoi.common.config_ApiResponseHandler.java
 * </pre>
 * 
 * @date : 2019. 7. 12.
 * @author : hychoi
 */
@ControllerAdvice
@RestController
public class ApiResponseHandler implements ResponseBodyAdvice<Object> {

	private final Logger log = LoggerFactory.getLogger(ApiResponseHandler.class);

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return true;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {
		
		HttpStatus httpStatus = HttpStatus.OK;

		Object ret = null;

		if (body instanceof ApiResponseDto) {

			ApiResponseDto apiResponse = (ApiResponseDto) body;

			try {

				httpStatus = HttpStatus.valueOf(apiResponse.getCode());
				response.setStatusCode(httpStatus);

				ret = response;
			} catch (IllegalArgumentException ex) {
			}
		} else {

			for (Annotation annotation : returnType.getMethodAnnotations()) {

				if (annotation instanceof ResponseStatus) {
					ResponseStatus responseStatus = (ResponseStatus) annotation;
					httpStatus = responseStatus.value();
					break;
				}
			}

			ret = new ApiResponseDto(httpStatus.value(), httpStatus.getReasonPhrase(), body);

		}
		return ret;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler
	protected ApiErrorResponseDto handleError(CustomException e) {
		
		return ApiErrorResponseDto.builder()
				.code(e.getResultCode().name())
				.message(e.getResultCode().getMessage())
				.status(e.getResultCode().getStatus())				
				.build();
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler
	protected ApiErrorResponseDto handleError(Exception e) {
		
		return ApiErrorResponseDto.builder()
				.code(HttpStatusType.FAIL.name())
				.message(e.getMessage())
				.build();
	}
}
