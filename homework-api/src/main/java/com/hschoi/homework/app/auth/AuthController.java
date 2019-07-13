package com.hschoi.homework.app.auth;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hschoi.homework.app.user.dto.UserDto;
import com.hschoi.homework.app.user.entity.User;
import com.hschoi.homework.app.user.service.UserService;
import com.hschoi.homework.common.auth.dto.AuthDto;
import com.hschoi.homework.common.auth.service.AuthService;

/**
 * <pre>
 * com.hschoi.homework.app.auth_AuthController.java
 * </pre>
 * @date : 2019. 7. 12.
 * @author : hychoi
 */
@RestController
@RequestMapping(value = "api/{version}/auth")
public class AuthController {
	
	private final Logger log = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	private UserService userService;
	@Autowired
	private AuthService authService;
	
	/**
	 * <pre>
	 * 1. 개요 : 회원가입
	 * 2. 처리내용 : 이메일, 비밀번호, 승객/기사여부를 파라미터로 받아 회원 가입 처리를 하는 API
	 * </pre>
	 * @Method Name : register
	 * @date : 2019. 7. 12.
	 * @author : hychoi
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 7. 13.		hychoi				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param userDto
	 * @return
	 */ 	
	@PostMapping("/sign")
    public ResponseEntity<AuthDto> register(@Valid @RequestBody UserDto userDto) {
        
		log.info("[AuthController]: >> 회원가입 : {}", userDto);
		//회원 가입 처리
        UserDto user = userService.register(userDto);
        // 성공 시 리턴된 UserDto를 파라미터로 Access Token 발행 후 Token 정보를 return
        String accessToken = authService.createUserKey(user);

		AuthDto tokenDto = AuthDto.builder()
						  .accessToken(accessToken)
						  .build();
        
        return ResponseEntity.status(HttpStatus.CREATED).body(tokenDto);
    }
	
	@PostMapping("/login")
	public ResponseEntity<AuthDto> userLogin(@Valid @RequestBody UserDto userDto) {
		
		log.info("[AuthController]: >> 로그인 : {}", userDto);
		UserDto loginUser = userService.login(userDto.getEmail(), userDto.getPassword());
		
		String accessToken = authService.createUserKey(loginUser);
		
		AuthDto tokenDto = AuthDto.builder()
    					  .accessToken(accessToken)
    					  .build(); 
		
		return ResponseEntity.ok().body(tokenDto);
	}
	
	@PostMapping("/refresh")
    public ResponseEntity<AuthDto> refresh(HttpServletRequest request) {
		
		log.info("[AuthController]: >> 토큰갱신 : {}", request);
        
		String token = authService.getTokenFromRequest(request);
        String email = authService.getEmailFromToken(token);

        User user = userService.findByUserEmail(email);
        String accessToken = authService.createUserKey(user.toUserDto());

        AuthDto tokenDto = AuthDto.builder()
				  		  .accessToken(accessToken)
				  		  .build(); 

        return ResponseEntity.ok().body(tokenDto);
    }
}
