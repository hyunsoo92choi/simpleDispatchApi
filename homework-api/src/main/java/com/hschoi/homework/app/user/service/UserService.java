package com.hschoi.homework.app.user.service;

import com.hschoi.homework.app.user.dto.UserDto;
import com.hschoi.homework.app.user.entity.User;

/**
 * <pre>
 * com.hschoi.homework.app.user.service_UserService.java
 * </pre>
 * @date : 2019. 7. 12.
 * @author : hychoi
 */
public interface UserService {
	/* 회원가입 및 로그인 담당 서비스 */
	public UserDto register(UserDto user);
	public UserDto login(String userEmail, String password);
	public User findByUserEmail(String userEmail);
}
