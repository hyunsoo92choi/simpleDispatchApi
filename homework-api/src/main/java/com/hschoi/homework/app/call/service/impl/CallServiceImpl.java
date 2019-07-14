package com.hschoi.homework.app.call.service.impl;

import static com.hschoi.common.code.HttpStatusType.INVALID_ADDRESS;
import static com.hschoi.common.code.HttpStatusType.ALREADY_ASSIGNED;
import static com.hschoi.common.code.HttpStatusType.NOT_FOUND_CALL_ID;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hschoi.common.code.CallStatusType;
import com.hschoi.common.exception.CustomException;
import com.hschoi.homework.app.call.dto.CallDto;
import com.hschoi.homework.app.call.dto.RequestDto;
import com.hschoi.homework.app.call.entity.Call;
import com.hschoi.homework.app.call.repository.CallRepository;
import com.hschoi.homework.app.call.service.CallService;
import com.hschoi.homework.app.user.entity.User;

/**
 * <pre>
 * com.hschoi.homework.app.call.service.impl_CallServiceImpl.java
 * </pre>
 * @date : 2019. 7. 12.
 * @author : hychoi
 */
@Service
public class CallServiceImpl implements CallService {
	
	@Autowired(required = false)
	private CallRepository callRepository;
	
	@Override
	@Transactional
	public CallDto setRequest(User user, RequestDto requestDto) {
		
		if (requestDto == null || StringUtils.length(requestDto.getAddress()) > 100) {
			throw new CustomException(INVALID_ADDRESS);
		}
		
		Call call = Call.builder()
						.callStatusType(CallStatusType.REQUESTED)
						.address(requestDto.getAddress())
						.requestedAt(new Date())
						.createdAt( LocalDateTime.now())
						.updatedAt( LocalDateTime.now())						
						.passenger(user)
						.build();
		
		return callRepository.save(call).toCallDto(call);
	}

	/**
	 * <pre>
	 * 1. 개요 : 목록 조회
	 * 2. 처리내용 : 전체의 배차 요청의 목록을 응답합니다.
	 * 				 배차가 완료된 요청과, 대기중인 요청 모두 목록에 표시
	 * 				 배차 대기 중인지, 완료된 요청인지 목록에 표시
	 * 				 배차 요청 시간과 배차 완료 시간을 목록에 표시
	 * 				 최근 추가된 요청부터 목록에 표시 (DESC)
	 * </pre>
	 * @Method Name : list
	 * @date : 2019. 7. 13.
	 * @author : hychoi
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 7. 13.		hychoi				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param pageable
	 * @return List<CallDto>
	 */ 	
	@Override
	public List<CallDto> list(Pageable pageable) {
		
		List<Call> callPage = callRepository.findAllBy(pageable);
		
		return mapToCallList(callPage);
		 
	}
	
	 private List<CallDto> mapToCallList(List<Call> listData) {
	        return listData.stream()
	                .map(Call::toCallDto)
	                .collect(Collectors.toList());
	    }

	@Override
	public CallDto setAssign(User user, RequestDto requestDto) {

		Call call = callRepository
				.findById(requestDto.getId())
						.orElseThrow(() -> new CustomException(NOT_FOUND_CALL_ID));

		if (CallStatusType.ASSIGNED.equals(call.getCallStatusType())) {
			throw new CustomException(ALREADY_ASSIGNED);
		}
		
		call.setCallStatusType(CallStatusType.ASSIGNED);
		call.setAssignedAt(new Date());
		call.setUpdatedAt(LocalDateTime.now());
		call.setDriver(user);
		
		return callRepository.save(call).toCallDto(call);
	}

}
