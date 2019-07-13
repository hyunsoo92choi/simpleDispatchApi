package com.hschoi.homework.common.auth.service;

import static com.hschoi.common.code.HttpStatusType.UNAUTHORIZED_REQUEST;
import static com.hschoi.common.code.HttpStatusType.USER_NOT_FOUND;
import static com.hschoi.common.code.HttpStatusType.REQUEST_ONLY_DRIVER;
import static com.hschoi.common.code.HttpStatusType.REQUEST_ONLY_PASSENGER;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hschoi.common.code.UserType;
import com.hschoi.common.exception.CustomException;
import com.hschoi.homework.app.user.dto.UserDto;
import com.hschoi.homework.app.user.entity.User;
import com.hschoi.homework.app.user.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * <pre>
 * com.hschoi.homework.common.auth.service_AuthService.java
 * </pre>
 * @date : 2019. 7. 12.
 * @author : hychoi
 */
@Service
public class AuthService {
	
	private final Logger log = LoggerFactory.getLogger(AuthService.class);
	
	private static final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    private static final String SECRET_KEY = "secret_key";
    private static final String HEADER_AUTH = "Authorization";
    private static final String HEADER_INC = "Bearer ";
    private static final int HOUR = 60 * 60 * 1000;
    
    @Autowired
    private UserService userService;
    
    /**
     * <pre>
     * 1. 개요 : 
     * 2. 처리내용 : 
     * </pre>
     * @Method Name : createUserKey
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
    public String createUserKey(UserDto user) {
        
    	if (user == null || StringUtils.isEmpty(user.getEmail())) {
            throw new CustomException(USER_NOT_FOUND);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("email", user.getEmail());
        map.put("userType", user.getUserType());        

        return generateJWT(map);
    }
    
    /**
     * <pre>
     * 1. 개요 : 
     * 2. 처리내용 : 
     * </pre>
     * @Method Name : generateJWT
     * @date : 2019. 7. 12.
     * @author : hychoi
     * @history : 
     *	-----------------------------------------------------------------------
     *	변경일				작성자						변경내용  
     *	----------- ------------------- ---------------------------------------
     *	2019. 7. 12.		hychoi				최초 작성 
     *	-----------------------------------------------------------------------
     * 
     * @param claimsMap
     * @return
     */ 	
    private String generateJWT(Map<String, Object> claimsMap) {
        
    	Date expireTime = new Date();
        expireTime.setTime(expireTime.getTime() + HOUR);

        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("type", "JWT");
        headerMap.put("algo", "HS256");

        JwtBuilder builder = Jwts.builder().setHeader(headerMap)
            .setClaims(claimsMap)
            .setExpiration(expireTime)
            .signWith(signatureAlgorithm, new SecretKeySpec(
                DatatypeConverter.parseBase64Binary(SECRET_KEY), signatureAlgorithm.getJcaName()));

        return builder.compact();
    }

    public String getEmailFromToken(String jwt) {
        return getUserFromToken(jwt, "email");
    }

    public UserType getUserTypeFromToken(String jwt) {
        return UserType.of(getUserFromToken(jwt, "userType"));
    }

    private String getUserFromToken(String jwt, String type) {
        
    	if (StringUtils.isEmpty(jwt)) {
    		throw new CustomException(USER_NOT_FOUND);
        }

        try {
        	
            Claims claims = Jwts.parser()
            		.setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
            		.parseClaimsJws(jwt).getBody();

            log.info("expireTime :" + claims.getExpiration());
            log.info("email : {}, userType: {}", claims.get("email"), claims.get("userType") );

            return (String) claims.get(type);
            
        } catch (ExpiredJwtException exception) {
            log.info("만료된 토큰");
        }
        
        throw new CustomException(UNAUTHORIZED_REQUEST);
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        
    	if (request == null) {
            return StringUtils.EMPTY;
        }

        final String authHeader = request.getHeader(HEADER_AUTH);
        
        if (authHeader == null || !authHeader.startsWith(HEADER_INC)) {
            throw new CustomException(UNAUTHORIZED_REQUEST);
        }

        return authHeader.substring(HEADER_INC.length());

    }

    /**
     * <pre>
     * 1. 개요 : 인증된 사용자 반환
     * 2. 처리내용 : 
     * </pre>
     * @Method Name : checkAuthAndGetUser
     * @date : 2019. 7. 12.
     * @author : hychoi
     * @history : 
     *	-----------------------------------------------------------------------
     *	변경일				작성자						변경내용  
     *	----------- ------------------- ---------------------------------------
     *	2019. 7. 12.		hychoi				최초 작성 
     *	-----------------------------------------------------------------------
     * 
     * @param userTypeCode
     * @param request
     * @return
     */ 	
    public User checkAuthAndGetUser(UserType userTypeCode, HttpServletRequest request) {
        String token = getTokenFromRequest(request);
        UserType userType = getUserTypeFromToken(token);

        switch (userTypeCode) {
            case PASSENGER:
                if (!userTypeCode.equals(userType)) {
                    throw new CustomException(REQUEST_ONLY_PASSENGER);
                }
                break;
            case DRIVER:
                if (!userTypeCode.equals(userType)) {
                    throw new CustomException(REQUEST_ONLY_DRIVER);
                }
                break;
            default:
                throw new CustomException(UNAUTHORIZED_REQUEST);
        }

        String email = getEmailFromToken(token);
        return userService.findByUserEmail(email);
    }

}
