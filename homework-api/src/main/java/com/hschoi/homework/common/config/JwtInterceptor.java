package com.hschoi.homework.common.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.hschoi.homework.common.auth.service.AuthService;

/**
 * <pre>
 * com.hschoi.homework.common.config_JwtInterceptor.java
 * </pre>
 * @date : 2019. 7. 12.
 * @author : hychoi
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {
	
	@Autowired
	private AuthService authService;
    
	@Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        String token = authService.getTokenFromRequest(req);
        String email = authService.getEmailFromToken(token);

        if (StringUtils.isEmpty(email)) {
            return false;
        }

        return true;
    }
}
