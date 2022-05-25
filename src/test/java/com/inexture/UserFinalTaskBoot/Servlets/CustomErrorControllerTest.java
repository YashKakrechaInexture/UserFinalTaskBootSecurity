package com.inexture.UserFinalTaskBoot.Servlets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.eclipse.jetty.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@WebMvcTest(CustomErrorController.class)
class CustomErrorControllerTest {

	@Autowired
	protected MockMvc mvc;
	
	HttpServletRequest request;
	
	@Test
	void testError1() throws Exception {
		mvc.perform(get("/error"));
	}
	
//	@Test
//	void testError2() throws Exception {
////		when(request.getAttribute(any())).thenReturn(HttpStatus.NOT_FOUND_404);
//		mvc.perform(get("/error").param(RequestDispatcher.ERROR_STATUS_CODE, HttpStatus.NOT_FOUND_404));
//	}

}
