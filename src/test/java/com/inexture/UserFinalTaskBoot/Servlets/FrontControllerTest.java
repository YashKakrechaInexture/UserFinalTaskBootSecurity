package com.inexture.UserFinalTaskBoot.Servlets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.RETURNS_SELF;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import com.inexture.UserFinalTaskBoot.Beans.AddressBean;
import com.inexture.UserFinalTaskBoot.Beans.UserBean;
import com.inexture.UserFinalTaskBoot.DAO.AddressDaoInterface;
import com.inexture.UserFinalTaskBoot.DAO.DaoInterface;
import com.inexture.UserFinalTaskBoot.Services.UserService;
import com.inexture.UserFinalTaskBoot.Utilities.ShaEncryption;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc
@WebMvcTest(FrontController.class)
class FrontControllerTest{

	@Autowired
	protected MockMvc mvc;
	
//	@Autowired
//	WebApplicationContext webApplicationContext;
	
	@MockBean
    private DaoInterface dm;

    @MockBean
    private AddressDaoInterface am;

//	@MockBean
//	private FrontController fc;

    @Mock
    ShaEncryption sha;
    
//    @Mock
//    UserBean user;

    @MockBean
    private UserService userService;

//	@BeforeAll
//	void setMvc(){
//		mvc = MockMvcBuilders.webAppContextSetup((WebApplicationContext) fc).build();
//	}

	@Test
	void testIndex() throws Exception {
//		String uri = "/index";
//		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
//		int status = mvcResult.getResponse().getStatus();
//		assertEquals(200, status);
//		String content = mvcResult.getResponse().getContentAsString();

//		mvc.perform(get("/index"))
//				.andExpect(status().isOk());
		
//		mvc.perform(get("/index"))
//		.andExpect((ResultMatcher) RETURNS_SELF);


//		mvc.perform(get("/bars")
//                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.*", hasSize(1)));
	}

	@Test
	void testHomepage() throws Exception {
	}

	@Test
	void testRegister() throws Exception {
	}

	@Test
	void testResetPassword() throws Exception {
	}

	@Test
	void testTemp() throws Exception {
	}

	@Test
	void testNewPassword1() throws Exception {
		
		mvc.perform(post("/newPassword")
						.param("email",""))
			.andExpect(status().isOk());
	}
	
	@Test
	void testNewPassword2() throws Exception {
		
//		mvc.perform(post("/newPassword")
//						.param("email","fsdhj@gmail.com"))
//			.andExpect((ResultMatcher) RETURNS_SELF);
	}

	@Test
	void testLogin() throws Exception {
		mvc.perform(post("/loginServlet")
						.param("email","fsdhj@gmail.com")
						.param("password","fsdhj"))
				.andExpect(status().isOk());
	}

	@Test
	void testAdmin() throws Exception {
		mvc.perform(post("/adminServlet"))
			.andExpect(status().isOk());
	}

	@Test
	void testLogout() throws Exception {
		mvc.perform(post("/logoutServlet"))
			.andExpect(status().isOk());
	}

	@Test
	void testAuthEmailServlet() throws Exception {
		mvc.perform(post("/authEmailServlet")
						.param("email","fsdhj@gmail.com"))
			.andExpect(status().isOk());
	}

	@Test
	void testDeleteServlet() throws Exception {
		mvc.perform(post("/deleteServlet")
						.param("uid","800"))
			.andExpect(redirectedUrl("adminServlet"));
	}

	@Test
	void testEditServlet() throws Exception {
		mvc.perform(post("/editServlet")
						.param("email","fsdhj@gmail.com"))
			.andExpect(status().isOk());
	}

	@Test
	void testNewPasswordServlet1() throws Exception {
		mvc.perform(post("/newPasswordServlet")
						.param("email","fsdhj@gmail.com")
						.param("password1","")
						.param("password2","sevrd"))
			.andExpect(status().isOk());
	}
	
	@Test
	void testNewPasswordServlet2() throws Exception {
		mvc.perform(post("/newPasswordServlet")
						.param("email","fsdhj@gmail.com")
						.param("password1","efs")
						.param("password2",""))
			.andExpect(status().isOk());
	}
	
	@Test
	void testNewPasswordServlet3() throws Exception {
		mvc.perform(post("/newPasswordServlet")
						.param("email","fsdhj@gmail.com")
						.param("password1","")
						.param("password2",""))
			.andExpect(status().isOk());
	}
	
//	@Test
//	void testNewPasswordServlet4() throws Exception {
//		mvc.perform(post("/newPasswordServlet")
//						.param("email","fsdhj@gmail.com")
//						.param("password1",null)
//						.param("password2",""))
//			.andExpect(status().isOk());
//	}
//	
//	@Test
//	void testNewPasswordServlet5() throws Exception {
//		mvc.perform(post("/newPasswordServlet")
//						.param("email","fsdhj@gmail.com")
//						.param("password1","")
//						.param("password2",null))
//			.andExpect(status().isOk());
//	}
	
	@Test
	void testNewPasswordServlet6() throws Exception {
		mvc.perform(post("/newPasswordServlet")
						.param("email","fsdhj@gmail.com")
						.param("password1","fsdhj")
						.param("password2","fsdhj"))
			.andExpect(status().isOk());
	}
	
	@Test
	void testNewPasswordServlet7() throws Exception {
		mvc.perform(post("/newPasswordServlet")
						.param("email","fsdhj@gmail.com")
						.param("password1","abnc")
						.param("password2","vhjmn"))
			.andExpect(status().isOk());
	}

	@Test
	void testResetPasswordServlet() throws Exception {
		mvc.perform(post("/resetPasswordServlet")
						.param("email","fsdhj@gmail.com")
						.param("birthdate","abnc")
						.param("que1","A")
						.param("que2","B")
						.param("que3","C"))
			.andExpect(status().isOk());
	}

	@Test
	void testRegisterServlet1() throws Exception {
		mvc.perform(post("/registerServlet")
						.contentType(MediaType.MULTIPART_FORM_DATA)
						.accept(MediaType.MULTIPART_FORM_DATA)
						.param("fname","abc")
						.param("lname","def")
						.param("email","fsdhj@gmail.com")
						.param("phone","4568451")
						.param("gender","Male")
						.param("birthdate","abnc")
						.param("hobby","Abc")
						.param("password1","fsdhj")
						.param("password2","fsdhj")
						.param("que1","A")
						.param("que2","B")
						.param("que3","C"))
			.andExpect(status().isOk());
	}
	
	@Test
	void testRegisterServlet2() throws Exception {
		
		FileInputStream filein = new FileInputStream(new File("C:\\Users\\Yash\\Downloads\\bg.jpg"));
		
		MockMultipartFile mockfilein = new MockMultipartFile("profilepic", "bg.jpg", "multipart/form-data",filein);
		
//		List<AddressBean> address = new ArrayList<AddressBean>();
		
//		AddressBean addr = new AddressBean("84 ecs","Ahmedabad","Gujarat","India","123456");
		
//		MultiValueMap<String, String> homeparams = new LinkedMultiValueMap<>();
//		MultiValueMap<String, String> cityparams = new LinkedMultiValueMap<>();
//		MultiValueMap<String, String> stateparams = new LinkedMultiValueMap<>();
//		MultiValueMap<String, String> countryparams = new LinkedMultiValueMap<>();
//		MultiValueMap<String, String> pincodeparams = new LinkedMultiValueMap<>();
//		
//		homeparams.add("home", "846");
//		cityparams.add("city", "ahmedabad");
//		stateparams.add("state", "gujarat");
//		countryparams.add("country", "india");
//		pincodeparams.add("pin", "0123456");
		
		mvc.perform(MockMvcRequestBuilders.fileUpload("/registerServlet")
						.file(mockfilein)
						.param("home","15")
						.param("city", "ahmedabad")
						.param("state", "gujarat")
						.param("country", "india")
						.param("pincode", "0123456")
						.contentType(MediaType.MULTIPART_FORM_DATA)
						.accept(MediaType.MULTIPART_FORM_DATA)
						.param("fname","abc")
						.param("lname","def")
						.param("email","fsdhj@gmail.com")
						.param("phone","4568451")
						.param("gender","Male")
						.param("birthdate","abnc")
						.param("hobby","Abc")
						.param("profilepic","Abc")
						.param("password1","fsdhj")
						.param("password2","fsdhj")
						.param("que1","A")
						.param("que2","B")
						.param("que3","C")
						.param("home[0]","84 ecs")
						.param("city[0]","Ahmedabad")
						.param("state[0]","Gujarat")
						.param("country[0]","India")
						.param("pincode[0]","123456"))
			.andExpect(status().isOk());
	}
	
	@Test
	void testRegisterServlet3() throws Exception {
		mvc.perform(post("/registerServlet")
						.contentType(MediaType.MULTIPART_FORM_DATA)
						.accept(MediaType.MULTIPART_FORM_DATA)
						.param("fname","abc")
						.param("lname","def")
						.param("email","fsdhj@gmail.com")
						.param("phone","4568451")
						.param("gender","Male")
						.param("birthdate","abnc")
						.param("hobby","Abc")
						.param("password1","fsdhj")
						.param("password2","hjvhkja")
						.param("que1","A")
						.param("que2","B")
						.param("que3","C"))
			.andExpect(status().isOk());
	}

	@Test
	void testUpdateServlet1() throws Exception {
		FileInputStream filein = new FileInputStream(new File("C:\\Users\\Yash\\Downloads\\bg.jpg"));
		
		MockMultipartFile mockfilein = new MockMultipartFile("profilepic", "bg.jpg", "multipart/form-data",filein);
		
//		List<AddressBean> address = new ArrayList<AddressBean>();
		
//		AddressBean addr = new AddressBean("84 ecs","Ahmedabad","Gujarat","India","123456");
		
//		MultiValueMap<String, String> homeparams = new LinkedMultiValueMap<>();
//		MultiValueMap<String, String> cityparams = new LinkedMultiValueMap<>();
//		MultiValueMap<String, String> stateparams = new LinkedMultiValueMap<>();
//		MultiValueMap<String, String> countryparams = new LinkedMultiValueMap<>();
//		MultiValueMap<String, String> pincodeparams = new LinkedMultiValueMap<>();
//		
//		homeparams.add("home", "846");
//		cityparams.add("city", "ahmedabad");
//		stateparams.add("state", "gujarat");
//		countryparams.add("country", "india");
//		pincodeparams.add("pin", "0123456");
		
		mvc.perform(MockMvcRequestBuilders.fileUpload("/updateServlet")
						.file(mockfilein)
						.param("home","15")
						.param("city", "ahmedabad")
						.param("state", "gujarat")
						.param("country", "india")
						.param("pincode", "0123456")
						.contentType(MediaType.MULTIPART_FORM_DATA)
						.accept(MediaType.MULTIPART_FORM_DATA)
						.param("fname","abc")
						.param("lname","def")
						.param("email","fsdhj@gmail.com")
						.param("phone","4568451")
						.param("gender","Male")
						.param("birthdate","abnc")
						.param("hobby","Abc")
						.param("profilepic","Abc")
						.param("password1","fsdhj")
						.param("password2","fsdhj")
						.param("que1","A")
						.param("que2","B")
						.param("que3","C")
						.param("home[0]","84 ecs")
						.param("city[0]","Ahmedabad")
						.param("state[0]","Gujarat")
						.param("country[0]","India")
						.param("pincode[0]","123456"))
			.andExpect(status().isOk());
	}
	
	@Test
	void testUpdateServlet2() throws Exception {
		FileInputStream filein = new FileInputStream(new File("C:\\Users\\Yash\\Downloads\\bg.jpg"));
		
		MockMultipartFile mockfilein = new MockMultipartFile("profilepic", "bg.jpg", "multipart/form-data",filein);
		
//		List<AddressBean> address = new ArrayList<AddressBean>();
		
//		AddressBean addr = new AddressBean("84 ecs","Ahmedabad","Gujarat","India","123456");
		
//		MultiValueMap<String, String> homeparams = new LinkedMultiValueMap<>();
//		MultiValueMap<String, String> cityparams = new LinkedMultiValueMap<>();
//		MultiValueMap<String, String> stateparams = new LinkedMultiValueMap<>();
//		MultiValueMap<String, String> countryparams = new LinkedMultiValueMap<>();
//		MultiValueMap<String, String> pincodeparams = new LinkedMultiValueMap<>();
//		
//		homeparams.add("home", "846");
//		cityparams.add("city", "ahmedabad");
//		stateparams.add("state", "gujarat");
//		countryparams.add("country", "india");
//		pincodeparams.add("pin", "0123456");
		
		mvc.perform(MockMvcRequestBuilders.fileUpload("/updateServlet")
						.param("home","15")
						.param("city", "ahmedabad")
						.param("state", "gujarat")
						.param("country", "india")
						.param("pincode", "0123456")
						.contentType(MediaType.MULTIPART_FORM_DATA)
						.accept(MediaType.MULTIPART_FORM_DATA)
						.param("fname","abc")
						.param("lname","def")
						.param("email","fsdhj@gmail.com")
						.param("phone","4568451")
						.param("gender","Male")
						.param("birthdate","abnc")
						.param("hobby","Abc")
						.param("profilepic","Abc")
						.param("password1","fsdhj")
						.param("password2","fsdhj")
						.param("que1","A")
						.param("que2","B")
						.param("que3","C")
						.param("home[0]","84 ecs")
						.param("city[0]","Ahmedabad")
						.param("state[0]","Gujarat")
						.param("country[0]","India")
						.param("pincode[0]","123456"))
			.andExpect(status().isOk());
	}

}
