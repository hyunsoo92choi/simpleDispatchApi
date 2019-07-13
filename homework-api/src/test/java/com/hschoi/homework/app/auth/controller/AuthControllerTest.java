package com.hschoi.homework.app.auth.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.hschoi.homework.common.TestConfig;

/**
 * <pre>
 * com.hschoi.homework.app.auth.controller_AuthControllerTest.java
 * </pre>
 * @date : 2019. 7. 12.
 * @author : hychoi
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfig.class)
public class AuthControllerTest {
	
	@Rule
	public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;
	private RestDocumentationResultHandler document;
    
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
    }
	
	@Test
    public void 회원가입() throws Exception {
        
		mockMvc.perform(
                post("/api/{version}/auth/sign","v1")
                .param("email", "hychoi@ebay.com")
                .param("password", "homework12#")
                .param("userType", "DRIVER")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
//                .andExpect(status().isOk())
                ;
    }
	
	@Test
    public void 로그인() throws Exception {
        mockMvc.perform(
                post("/api/{version}/auth/login","v1")
                .param("email", "hychoi@ebay.com")
                .param("password", "homework12#")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
//                .andExpect(status().isOk())
                ;
    }
}
