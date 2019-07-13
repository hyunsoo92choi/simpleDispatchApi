package com.hschoi.homework.app.call.service.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
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

import static com.hschoi.common.code.HttpStatusType.INVALID_ADDRESS;

/**
 * <pre>
 * com.hschoi.homework.app.call.service.impl_CallServiceImpl.java
 * </pre>
 * @date : 2019. 7. 12.
 * @author : hychoi
 */
@Service
public class CallServiceImpl implements CallService {
	
	private final CallRepository repository;

	public CallServiceImpl(CallRepository repository) {
		this.repository = repository;
	}
	
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
		            .passenger(user)
		            .build();
		 return repository.save(call).toCallDto(call);
	}
	
	@Override
	public List<CallDto> list(Pageable pageable) {
		return null;
	}

}
