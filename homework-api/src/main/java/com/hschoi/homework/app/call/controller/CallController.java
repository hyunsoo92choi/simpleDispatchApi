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
	private UserService userService;
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
    public ResponseEntity<CallDto> request(@RequestBody RequestDto requestInfo, HttpServletRequest request) {
        
    	log.info("[CallController]: >> 승차요청 : {}", request);
    	
    	User user = authService.checkAuthAndGetUser(UserType.PASSENGER, request);
        CallDto callDto = callService.setRequest(user, requestInfo);

        return ResponseEntity.status(HttpStatus.CREATED).body(callDto);
    }
    
    @GetMapping("/list")
    public List<CallDto> list(
        @PageableDefault(sort = {"id"}, direction = Direction.DESC, size = 10) Pageable pageable) {
    	log.info("[CallController]: >> 요청조회 : {}" );
    	
        return callService.list(pageable);
    }
}
