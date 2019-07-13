package com.hschoi.homework.app.auth.service;

import static junit.framework.TestCase.assertFalse;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.hschoi.homework.app.user.dto.UserDto;
import com.hschoi.homework.app.user.entity.User;
import com.hschoi.homework.common.auth.service.AuthService;

/**
 * <pre>
 * com.hschoi.homework.app.auth.service_AuthServiceTest.java
 * </pre>
 * 
 * @date : 2019. 7. 13.
 * @author : hychoi
 */
@RunWith(SpringRunner.class)
public class AuthServiceTest {

	private AuthService service;

	@Before
	public void setUp() {
		service = new AuthService();
	}
	// createUserKey Method Test
	@Test
	public void userKey_결과값이_있을때() {
		UserDto userDto = User.builder().email("hychoi@ebay.com").build().toUserDto();
		String userKey = service.createUserKey(userDto);
		assertFalse(StringUtils.isEmpty(userKey));
	}

}
