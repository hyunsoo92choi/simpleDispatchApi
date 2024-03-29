package com.hschoi.homework.app.call.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hschoi.common.code.UserType;
import com.hschoi.homework.app.call.dto.CallDto;
import com.hschoi.homework.app.call.dto.RequestDto;
import com.hschoi.homework.app.call.service.CallService;
import com.hschoi.homework.app.user.entity.User;
import com.hschoi.homework.app.user.service.UserService;
import com.hschoi.homework.common.auth.service.AuthService;

/**
 * <pre>
 * com.hschoi.homework.app.call.controller_CallController.java
 * </pre>
 * @date : 2019. 7. 12.
 * @author : hychoi
 */
@RestController
@RequestMapping(value = "api/{version}/call")
public class CallController {
	
	private final Logger log = LoggerFactory.getLogger(CallController.class);

	@Autowired
	private AuthService authService;
	@Autowired
	private CallService callService;
	
    /**
     * <pre>
     * 1. 개요 : "승객"이 특정 "장소"에서 택시를 타고 싶다고 요청
     * 			 "주소"를 파라미터로 받습니다.
     *           "주소"는 100글자 이내의 String
     * 2. 처리내용 : 요청한 사람이 승객인지 인증 후 사용자 정보를 이용하여 
     * 				 택시를 타고 싶다는 요청을 생성 함.
     * </pre>
     * @Method Name : request
     * @date : 2019. 7. 13.
     * @author : hychoi
     * @history : 
     *	-----------------------------------------------------------------------
     *	변경일				작성자						변경내용  
     *	----------- ------------------- ---------------------------------------
     *	2019. 7. 13.		hychoi				최초 작성 
     *	-----------------------------------------------------------------------
     * 
     * @param RequestDto
     * @param request
     * @return CallDto
     */ 	
    @PostMapping("/request")
    public ResponseEntity<CallDto> request(@RequestBody RequestDto requestDto, HttpServletRequest request) {
        
    	log.info("[CallController]: >> 승차요청 : {}", request);
    	
    	User user = authService.checkAuthAndGetUser(UserType.PASSENGER, request);
        CallDto callDto = callService.setRequest(user, requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(callDto);
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
    @GetMapping("/list")
    public ResponseEntity<List<CallDto>> list(
        @PageableDefault(sort = {"id"}, direction = Direction.DESC, size = 3) Pageable pageable) {
    	log.info("[CallController]: >> 요청조회 : {}" );
    	
    	List<CallDto> callDtoList = callService.list(pageable);
    	
    	return ResponseEntity.status(HttpStatus.OK).body(callDtoList);
    }
    
    /**
     * <pre>
     * 1. 개요 : 승차요청에 대한 배차 요청을 한다.
     * 2. 처리내용 : 승차 요청시 생성된 요청 ID를 받아 배차 요청을 한다.
     * </pre>
     * @Method Name : assign
     * @date : 2019. 7. 14.
     * @author : hychoi
     * @history : 
     *	-----------------------------------------------------------------------
     *	변경일				작성자						변경내용  
     *	----------- ------------------- ---------------------------------------
     *	2019. 7. 14.		hychoi				최초 작성 
     *	-----------------------------------------------------------------------
     * 
     * @param requestDto
     * @param request
     * @return CallDto
     */ 	
    @PostMapping("/assign")
    public ResponseEntity<CallDto> assign(@RequestBody RequestDto requestDto, HttpServletRequest request) {
        
    	log.info("[CallController]: >> 배차요청: {}", request);
    	
    	User user = authService.checkAuthAndGetUser(UserType.DRIVER, request);

    	CallDto callDto = callService.setAssign(user, requestDto);

    	return ResponseEntity.status(HttpStatus.CREATED).body(callDto);
    }
}
