package com.inexture.UserFinalTaskBoot.Servlets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(classes = FrontController.class)
@WebAppConfiguration
public abstract class AbstractTest {
	
	@Autowired
	protected MockMvc mvc;
	
	@Autowired
	WebApplicationContext webApplicationContext;
}
