package com.hschoi.homework.app.user.service.impl;

import static com.hschoi.common.code.HttpStatusType.ACCOUNT_DUPLICATION;
import static com.hschoi.common.code.HttpStatusType.USER_NOT_FOUND;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hschoi.common.exception.CustomException;
import com.hschoi.homework.app.user.dto.UserDto;
import com.hschoi.homework.app.user.entity.User;
import com.hschoi.homework.app.user.repository.UserRepository;
import com.hschoi.homework.app.user.service.UserService;

/**
 * <pre>
 * com.hschoi.homework.app.user.service.impl_UserServiceImpl.java
 * </pre>
 * @date : 2019. 7. 12.
 * @author : hychoi
 */
@Service
public class UserServiceImpl implements UserService {
	
	private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	/**
	 * <pre>
	 * 1. 개요 : 회원 가입 Method
	 * 2. 처리내용 : 이메일, 비밀번호, 승객/기사 여부를 담은 UserDto 객체를 가지고 회원 가입을 한다.
	 * 				 이미 사용 중인 이메일로는 가입 할 수 없어야 함
	 * </pre>
	 * @Method Name : register
	 * @date : 2019. 7. 12.
	 * @author : hychoi
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 7. 12.		hychoi				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param user
	 * @return
	 */ 	
	@Override
	public UserDto register(UserDto user) {
		// 유효성 체크
		verifyExist(user);        
		return userRepository
                .save(user.toEntity(passwordEncoder)).toUserDto();
	}
	
	/**
	 * <pre>
	 * 1. 개요 : 회원가입 유효성 체커
	 * 2. 처리내용 : 이미 사용중인 이메일로는 가입 할 수 없어야 함.
	 * </pre>
	 * @Method Name : verifyExist
	 * @date : 2019. 7. 12.
	 * @author : hychoi
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 7. 12.		hychoi				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param user
	 */ 	
	public void verifyExist(UserDto user) {
		
		log.info("[UserServiceImpl]: >> 회원 가입 유효성 체크 : {}", user);
		
		if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new CustomException(ACCOUNT_DUPLICATION);
        }
	}

	/**
	 * <pre>
	 * 1. 개요 : 로그인 Method
	 * 2. 처리내용 : email 정보로 사용자를 조회 후 파라미터로 입력받은 Password와 인코딩된 Password가 
	 * 				 일치하면 로그인 성공 
	 * </pre>
	 * @Method Name : login
	 * @date : 2019. 7. 12.
	 * @author : hychoi
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 7. 12.		hychoi				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param userEmail
	 * @param password
	 * @return
	 */ 	
	@Override
	public UserDto login(String userEmail, String password) {
		User user = findByUserEmail(userEmail);
        user.matchPassword(password, passwordEncoder);
        
        return user.toUserDto();
	}

	/**
	 * <pre>
	 * 1. 개요 : 이메일로 회원 조회
	 * 2. 처리내용 : email 계정을 파라미터로 해당 이메일을 사용하는 사용자가 있는지 확인하여
	 * 				 계정이 존재하면 return 없으면 사용자가 없다는 Exception 발생
	 * </pre>
	 * @Method Name : findByUserEmail
	 * @date : 2019. 7. 12.
	 * @author : hychoi
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 7. 12.		hychoi				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param userEmail
	 * @return User
	 */ 	
	@Override
	public User findByUserEmail(String userEmail) {
		return userRepository.findByEmail(userEmail)
				.orElseThrow(() -> new CustomException(USER_NOT_FOUND));
	}

}
