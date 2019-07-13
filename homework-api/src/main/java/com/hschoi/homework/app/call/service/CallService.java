package com.hschoi.homework.app.call.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.hschoi.homework.app.call.dto.CallDto;
import com.hschoi.homework.app.call.dto.RequestDto;
import com.hschoi.homework.app.call.entity.Call;
import com.hschoi.homework.app.user.entity.User;

/**
 * <pre>
 * com.hschoi.homework.app.call.service_CallService.java
 * </pre>
 * @date : 2019. 7. 12.
 * @author : hychoi
 */
public interface CallService {
	
	public CallDto setRequest(User user, RequestDto requestDto);
	
	/**
	 * <pre>
	 * 1. 개요 : 호출 목록 조회
	 * 2. 처리내용 : 
	 * </pre>
	 * @Method Name : list
	 * @date : 2019. 7. 12.
	 * @author : hychoi
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 7. 12.		hychoi				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param pageable
	 * @return
	 */ 	
	public List<CallDto> list(Pageable pageable);
	public CallDto setAssign(User user, RequestDto requestDto);
}
