package com.hschoi.homework.app.auth.controller;

import static com.hschoi.common.code.HttpStatusType.ACCOUNT_DUPLICATION;
import static com.hschoi.common.code.HttpStatusType.USER_NOT_FOUND;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hschoi.common.code.UserType;
import com.hschoi.common.exception.CustomException;
import com.hschoi.homework.app.auth.AuthController;
import com.hschoi.homework.app.call.controller.CallController;
import com.hschoi.homework.app.call.service.impl.CallServiceImpl;
import com.hschoi.homework.app.user.dto.UserDto;
import com.hschoi.homework.app.user.entity.User;
import com.hschoi.homework.app.user.service.UserService;
import com.hschoi.homework.common.auth.service.AuthService;

/**
 * <pre>
 * com.hschoi.homework.app.auth.controller_AuthControllerTest.java
 * </pre>
 * @date : 2019. 7. 12.
 * @author : hychoi
 */
@RunWith(SpringRunner.class)
@WebMvcTest({AuthController.class})
@EnableSpringDataWebSupport
@ComponentScan(basePackages = "com.hschoi")
public class AuthControllerTest {
	
	@Rule
	public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;
	private RestDocumentationResultHandler document;
	
	private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
	        MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8 );
	
	 private static final String ROOT_URI = "/api/v1/auth";

    private static final String HEADER_AUTH = "Authorization";
    private static final String HEADER_INC = "Bearer ";
    
    private final String normalToken = "eyJ0eXBlIjoiSldUIiwiYWxnIjoiSFMyNTYiLCJhbGdvIjoiSFMyNTYifQ.eyJ1c2VyVHlwZSI6IlBBU1NFTkdFUiIsImV4cCI6MTU2Mjk4OTI0NiwiZW1haWwiOiJoeWNob2lAZWJheS5jb20ifQ.9Kv0UlIC75XTFCVbUmBQTbJz9tEiemZfU2kxBQK7Kqc";
    
    private UserDto requestUser;

    private UserDto dbUser;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthService authservice;

    @MockBean
    private UserService userService;
    
	@Before
    public void setUp() {
        this.document = document(
                "{class-name}/{method-name}",
                preprocessResponse(prettyPrint())
        );
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply( documentationConfiguration(this.restDocumentation) )
                .alwaysDo(document)
                .build();
        
        requestUser = User.builder()
                .email("hychoi@ebay.com")
                .password("password12#")
                .userType(UserType.PASSENGER)
                .build().toUserDto();
            dbUser = new UserDto("hychoi@ebay.com" , "password12#", UserType.PASSENGER);
    }
	
	@Test
	public void 회원가입__성공() throws Exception {

		given(userService.register(any(UserDto.class))).willReturn(dbUser);
		given(authservice.createUserKey(any(UserDto.class))).willReturn(normalToken);

		doResultActions("/sign", requestUser).andExpect(status().isCreated())
				.andExpect(content().json("{'data':{'accessToken':" + normalToken + "}}"));

	}
	
	@Test
    public void 회원가입_실패() throws Exception {
		given(userService.register(any(UserDto.class))).willReturn(dbUser);
        given(authservice.createUserKey(any(UserDto.class))).willThrow(new CustomException(ACCOUNT_DUPLICATION));

        doResultActions("/signup", requestUser)
            .andExpect(status().isNotFound());
    }
	
	@Test
    public void 로그인__성공() throws Exception {
		
		given(userService.login( requestUser.getEmail(), requestUser.getPassword() ))
				.willReturn(dbUser);
		
		given(authservice.createUserKey(any(UserDto.class))).willReturn(normalToken);

		doResultActions("/login", requestUser)
            .andExpect(status().isOk())
            .andExpect(content().json("{'data':{'accessToken':" + normalToken + "}}"));
    }
	
	@Test
    public void 로그인__실패() throws Exception {
        given(userService.login( requestUser.getEmail(), requestUser.getPassword() ))
        		.willThrow(new CustomException(USER_NOT_FOUND));

        doResultActions("/login", requestUser)
            .andExpect(status().isBadRequest());
    }

	private ResultActions doResultActions(String url, UserDto userDto) throws Exception {
		String resultJson = new ObjectMapper().writeValueAsString(userDto);
		RequestBuilder rb = MockMvcRequestBuilders.post(ROOT_URI + url).contentType(APPLICATION_JSON_UTF8)
				.content(resultJson);
		return mvc.perform(rb).andDo(print());
	}
	
}
