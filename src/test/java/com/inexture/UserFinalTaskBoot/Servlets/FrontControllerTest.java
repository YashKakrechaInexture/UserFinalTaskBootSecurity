package com.inexture.UserFinalTaskBoot.Servlets;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.inexture.UserFinalTaskBoot.DAO.AddressDaoInterface;
import com.inexture.UserFinalTaskBoot.DAO.DaoInterface;
import com.inexture.UserFinalTaskBoot.Services.UserService;
import com.inexture.UserFinalTaskBoot.Utilities.ShaEncryption;

@SpringBootTest
@AutoConfigureMockMvc
class FrontControllerTest{
	
	@Autowired
	protected MockMvc mvc;
	
//	@Autowired
//	WebApplicationContext webApplicationContext;
	
	@Mock
    private DaoInterface dm;

    @Mock
    private AddressDaoInterface am;
    
    @Mock
    ShaEncryption sha;

    @InjectMocks
    private UserService userService;
	
	@Test
	void testIndex() throws Exception {
		String uri = "/index";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		
//		mvc.perform(get("/bars")
//                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.*", hasSize(1)));
	}

	@Test
	void testHomepage() {
	}

	@Test
	void testRegister() {
	}

	@Test
	void testResetPassword() {
	}

	@Test
	void testTemp() {
	}

	@Test
	void testNewPassword() {
	}

	@Test
	void testLogin() {
	}

	@Test
	void testAdmin() {
	}

	@Test
	void testLogout() {
	}

	@Test
	void testAuthEmailServlet() {
	}

	@Test
	void testDeleteServlet() {
	}

	@Test
	void testEditServlet() {
	}

	@Test
	void testNewPasswordServlet() {
	}

	@Test
	void testResetPasswordServlet() {
	}

	@Test
	void testRegisterServlet() {
	}

	@Test
	void testUpdateServlet() {
	}

}
