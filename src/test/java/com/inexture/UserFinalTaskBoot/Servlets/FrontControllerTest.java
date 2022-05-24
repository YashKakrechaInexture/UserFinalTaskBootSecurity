package com.inexture.UserFinalTaskBoot.Servlets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.RETURNS_SELF;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

import com.inexture.UserFinalTaskBoot.ViewConfigurationClass;
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
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

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
		
//		mvc.perform(get("/index"))
//				.andExpect(status().isOk());
		
//		mvc.perform(get("/index"))
//		.andExpect((ResultMatcher) RETURNS_SELF);

	}

	@Test
	void testHomepage() throws Exception {
//		mvc.perform(post("/homepage"))
//			.andExpect(view().name("homepage"));
//		mvc.perform(post("/homepage"))
//			.andExpect(status().is3xxRedirection());
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
//			.andExpect(view().name("newPassword"));
	}

	@Test
	void testLogin1() throws Exception {
		
		AddressBean addressBean = new AddressBean("ABC","ahmedabad","gujrat","india","132456");

        ArrayList<AddressBean> addressList = new ArrayList<>();
        addressList.add(addressBean);

        InputStream inputStream = new InputStream() {
            @Override
            public int read() throws IOException {
                return -1;
            }
        };

        UserBean userBean = new UserBean("yash","kakrecha","yash7890@gmail.com",1234567890,"yash123","Male","2022-01-01","Movies","A","B","C",addressList,inputStream);
        userBean.setType("user");
        
		when(userService.checkUser("fsdhj@gmail.com", "fsdhj")).thenReturn(userBean);
		
		mvc.perform(post("/loginServlet")
						.param("email","fsdhj@gmail.com")
						.param("password","fsdhj"))
				.andExpect(redirectedUrl("homepage"));
	}
	
	@Test
	void testLogin2() throws Exception {
		
		AddressBean addressBean = new AddressBean("ABC","ahmedabad","gujrat","india","132456");

        ArrayList<AddressBean> addressList = new ArrayList<>();
        addressList.add(addressBean);

        InputStream inputStream = new InputStream() {
            @Override
            public int read() throws IOException {
                return -1;
            }
        };

        UserBean userBean = new UserBean("yash","kakrecha","yash7890@gmail.com",1234567890,"yash123","Male","2022-01-01","Movies","A","B","C",addressList,inputStream);
        userBean.setType("admin");
        
		when(userService.checkUser("fsdhj@gmail.com", "fsdhj")).thenReturn(userBean);
		
		mvc.perform(post("/loginServlet")
						.param("email","fsdhj@gmail.com")
						.param("password","fsdhj"))
				.andExpect(redirectedUrl("adminServlet"));
	}
	
	@Test
	void testLogin3() throws Exception {
		
		AddressBean addressBean = new AddressBean("ABC","ahmedabad","gujrat","india","132456");

        ArrayList<AddressBean> addressList = new ArrayList<>();
        addressList.add(addressBean);

        InputStream inputStream = new InputStream() {
            @Override
            public int read() throws IOException {
                return -1;
            }
        };

        UserBean userBean = new UserBean("yash","kakrecha","yash7890@gmail.com",1234567890,"yash123","Male","2022-01-01","Movies","A","B","C",addressList,inputStream);
//        userBean.setType("admin");
        
		when(userService.checkUser("fsdhj@gmail.com", "fsdhj")).thenReturn(userBean);
				
		mvc.perform(post("/loginServlet")
						.param("email","fsdhj@gmail.com")
						.param("password","fsdhj"))
				.andExpect(redirectedUrl("index"));
	}
	
	@Test
	void testLogin4() throws Exception {
		
		AddressBean addressBean = new AddressBean("ABC","ahmedabad","gujrat","india","132456");

        ArrayList<AddressBean> addressList = new ArrayList<>();
        addressList.add(addressBean);

        InputStream inputStream = new InputStream() {
            @Override
            public int read() throws IOException {
                return -1;
            }
        };

        UserBean userBean = new UserBean("yash","kakrecha","yash7890@gmail.com",1234567890,"yash123","Male","2022-01-01","Movies","A","B","C",addressList,inputStream);
        userBean.setType("esbhbhs");
        
		when(userService.checkUser("fsdhj@gmail.com", "fsdhj")).thenReturn(userBean);
				
		mvc.perform(post("/loginServlet")
						.param("email","fsdhj@gmail.com")
						.param("password","fsdhj"))
				.andExpect(redirectedUrl("index"));
	}
	
	@Test
	void testLogin5() throws Exception {
		mvc.perform(post("/loginServlet")
						.param("email","fsdhj@gmail.com")
						.param("password","fsdhj"))
				.andExpect(forwardedUrl("index"));
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
	void testAuthEmailServlet1() throws Exception {
		
		when(userService.checkEmail("fsdhj@gmail.com")).thenReturn(true);
		
		mvc.perform(post("/authEmailServlet")
						.param("email","fsdhj@gmail.com"))
			.andExpect(status().isOk())
			.andExpect(content().string("<span style=\"color:green;\">Email Available.</span>"));
	}
	
	@Test
	void testAuthEmailServlet2() throws Exception {
		
		when(userService.checkEmail("fsdhj@gmail.com")).thenReturn(false);
		
		mvc.perform(post("/authEmailServlet")
						.param("email","fsdhj@gmail.com"))
			.andExpect(status().isOk())
			.andExpect(content().string("<span style=\"color:red;\">Email Already Taken.</span>"));
	}

	@Test
	void testDeleteServlet() throws Exception {
		mvc.perform(post("/deleteServlet")
						.param("uid","800"))
			.andExpect(redirectedUrl("adminServlet"));
	}

	@Test
	void testEditServlet1() throws Exception {
		mvc.perform(post("/editServlet")
						.param("email","fsdhj@gmail.com"))
			.andExpect(status().isOk());
	}
	
	@Test
	void testEditServlet2() throws Exception {
		
//		HttpSession session = mock(HttpSession.class);
		
		AddressBean addressBean = new AddressBean("ABC","ahmedabad","gujrat","india","132456");

        ArrayList<AddressBean> addressList = new ArrayList<>();
        addressList.add(addressBean);

        InputStream inputStream = new InputStream() {
            @Override
            public int read() throws IOException {
                return -1;
            }
        };

        UserBean userBean = new UserBean("yash","kakrecha","yash789@gmail.com",1234567890,"yash123","Male","2022-01-01","Movies","A","B","C",addressList,inputStream);
        
		
//		session.setAttribute("user", userBean);
		
//		HashMap<String, UserBean> sessionattr = new HashMap<String, UserBean>();
//		sessionattr.put("user", userBean);
		
		mvc.perform(post("/editServlet")
						.sessionAttr("user", userBean)
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
	void testResetPasswordServlet1() throws Exception {
		
		when(userService.findUser("fsdhj@gmail.com", "abnc", "A", "B", "C")).thenReturn(1);
		
		mvc.perform(post("/resetPasswordServlet")
						.param("email","fsdhj@gmail.com")
						.param("birthdate","abnc")
						.param("que1","A")
						.param("que2","B")
						.param("que3","C"))
			.andExpect(redirectedUrl("newPassword?email=fsdhj%40gmail.com"));
	}
	
	@Test
	void testResetPasswordServlet2() throws Exception {
				
		mvc.perform(post("/resetPasswordServlet")
						.param("email","fsdhj@gmail.com")
						.param("birthdate","abnc")
						.param("que1","A")
						.param("que2","B")
						.param("que3","C"))
			.andExpect(forwardedUrl("resetPassword"));
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
				
		AddressBean addressBean = new AddressBean("ABC","ahmedabad","gujrat","india","132456");

        ArrayList<AddressBean> addressList = new ArrayList<>();
        addressList.add(addressBean);

        InputStream inputStream = new InputStream() {
            @Override
            public int read() throws IOException {
                return -1;
            }
        };

        UserBean userBean = new UserBean("yash","kakrecha","yash7890@gmail.com",1234567890,"yash123","Male","2022-01-01","Movies","A","B","C",addressList,inputStream);
		
        when(userService.checkEmail(userBean.getEmail())).thenReturn(true);
		
		mvc.perform(MockMvcRequestBuilders.fileUpload("/registerServlet")
						.file(mockfilein)
						.contentType(MediaType.MULTIPART_FORM_DATA)
						.accept(MediaType.MULTIPART_FORM_DATA)
						.flashAttr("userBean", userBean)
						.param("password1","fsdhj")
						.param("password2","fsdhj"))
			.andExpect(redirectedUrl("index"));
	}
	
	@Test
	void testRegisterServlet3() throws Exception {
		
		FileInputStream filein = new FileInputStream(new File("C:\\Users\\Yash\\Downloads\\bg.jpg"));
		
		MockMultipartFile mockfilein = new MockMultipartFile("profilepic", "bg.jpg", "multipart/form-data",filein);
				
		AddressBean addressBean = new AddressBean("ABC","ahmedabad","gujrat","india","132456");

        ArrayList<AddressBean> addressList = new ArrayList<>();
        addressList.add(addressBean);

        InputStream inputStream = new InputStream() {
            @Override
            public int read() throws IOException {
                return -1;
            }
        };

        UserBean userBean = new UserBean("yash","kakrecha","yash7890@gmail.com",1234567890,"yash123","Male","2022-01-01","Movies","A","B","C",addressList,inputStream);
		userBean.setType("user");
        
        when(userService.checkEmail(userBean.getEmail())).thenReturn(true);
		
		mvc.perform(MockMvcRequestBuilders.fileUpload("/registerServlet")
						.file(mockfilein)
						.contentType(MediaType.MULTIPART_FORM_DATA)
						.accept(MediaType.MULTIPART_FORM_DATA)
						.flashAttr("userBean", userBean)
						.sessionAttr("user", userBean)
						.param("password1","fsdhj")
						.param("password2","fsdhj"))
			.andExpect(redirectedUrl("homepage"));
	}
	
	@Test
	void testRegisterServlet4() throws Exception {
		
		FileInputStream filein = new FileInputStream(new File("C:\\Users\\Yash\\Downloads\\bg.jpg"));
		
		MockMultipartFile mockfilein = new MockMultipartFile("profilepic", "bg.jpg", "multipart/form-data",filein);
				
		AddressBean addressBean = new AddressBean("ABC","ahmedabad","gujrat","india","132456");

        ArrayList<AddressBean> addressList = new ArrayList<>();
        addressList.add(addressBean);

        InputStream inputStream = new InputStream() {
            @Override
            public int read() throws IOException {
                return -1;
            }
        };

        UserBean userBean = new UserBean("yash","kakrecha","yash7890@gmail.com",1234567890,"yash123","Male","2022-01-01","Movies","A","B","C",addressList,inputStream);
		userBean.setType("admin");
        
        when(userService.checkEmail(userBean.getEmail())).thenReturn(true);
		
		mvc.perform(MockMvcRequestBuilders.fileUpload("/registerServlet")
						.file(mockfilein)
						.contentType(MediaType.MULTIPART_FORM_DATA)
						.accept(MediaType.MULTIPART_FORM_DATA)
						.flashAttr("userBean", userBean)
						.sessionAttr("user", userBean)
						.param("password1","fsdhj")
						.param("password2","fsdhj"))
			.andExpect(redirectedUrl("adminServlet"));
	}
	
	@Test
	void testRegisterServlet5() throws Exception {
		
		FileInputStream filein = new FileInputStream(new File("C:\\Users\\Yash\\Downloads\\bg.jpg"));
		
		MockMultipartFile mockfilein = new MockMultipartFile("profilepic", "bg.jpg", "multipart/form-data",filein);
				
		AddressBean addressBean = new AddressBean("ABC","ahmedabad","gujrat","india","132456");

        ArrayList<AddressBean> addressList = new ArrayList<>();
        addressList.add(addressBean);

        InputStream inputStream = new InputStream() {
            @Override
            public int read() throws IOException {
                return -1;
            }
        };

        UserBean userBean = new UserBean("yash","kakrecha","yash7890@gmail.com",1234567890,"yash123","Male","2022-01-01","Movies","A","B","C",addressList,inputStream);
		userBean.setType("ueje");
        
        when(userService.checkEmail(userBean.getEmail())).thenReturn(true);
		
		mvc.perform(MockMvcRequestBuilders.fileUpload("/registerServlet")
						.file(mockfilein)
						.contentType(MediaType.MULTIPART_FORM_DATA)
						.accept(MediaType.MULTIPART_FORM_DATA)
						.flashAttr("userBean", userBean)
						.sessionAttr("user", userBean)
						.param("password1","fsdhj")
						.param("password2","fsdhj"))
			.andExpect(redirectedUrl("register"));
	}
	
	@Test
	void testRegisterServlet6() throws Exception {
		
		FileInputStream filein = new FileInputStream(new File("C:\\Users\\Yash\\Downloads\\bg.jpg"));
		
		MockMultipartFile mockfilein = new MockMultipartFile("profilepic", "bg.jpg", "multipart/form-data",filein);
				
		AddressBean addressBean = new AddressBean("ABC","ahmedabad","gujrat","india","132456");

        ArrayList<AddressBean> addressList = new ArrayList<>();
        addressList.add(addressBean);

        InputStream inputStream = new InputStream() {
            @Override
            public int read() throws IOException {
                return -1;
            }
        };

        UserBean userBean = new UserBean("yash","kakrecha","yash7890@gmail.com",1234567890,"yash123","Male","2022-01-01","Movies","A","B","C",addressList,inputStream);
//		userBean.setType("ueje");
        
        when(userService.checkEmail(userBean.getEmail())).thenReturn(true);
		
		mvc.perform(MockMvcRequestBuilders.fileUpload("/registerServlet")
						.file(mockfilein)
						.contentType(MediaType.MULTIPART_FORM_DATA)
						.accept(MediaType.MULTIPART_FORM_DATA)
						.flashAttr("userBean", userBean)
						.sessionAttr("user", userBean)
						.param("password1","fsdhj")
						.param("password2","fsdhj"))
			.andExpect(redirectedUrl("register"));
	}
	
	@Test
	void testRegisterServlet7() throws Exception {
		FileInputStream filein = new FileInputStream(new File("C:\\Users\\Yash\\Downloads\\bg.jpg"));
		
		MockMultipartFile mockfilein = new MockMultipartFile("profilepic", "bg.jpg", "multipart/form-data",filein);
				
		AddressBean addressBean = new AddressBean("ABC","ahmedabad","gujrat","india","132456");

        ArrayList<AddressBean> addressList = new ArrayList<>();
        addressList.add(addressBean);

        InputStream inputStream = new InputStream() {
            @Override
            public int read() throws IOException {
                return -1;
            }
        };

        UserBean userBean = new UserBean("yash","kakrecha","yash7890@gmail.com",1234567890,"yash123","Male","2022-01-01","Movies","A","B","C",addressList,inputStream);
		
		mvc.perform(MockMvcRequestBuilders.fileUpload("/registerServlet")
						.file(mockfilein)
						.contentType(MediaType.MULTIPART_FORM_DATA)
						.accept(MediaType.MULTIPART_FORM_DATA)
						.flashAttr("userBean", userBean)
						.param("password1","fsdhj")
						.param("password2","vnhbm"))
			.andExpect(status().isOk());
	}
	
	@Test
	void testRegisterServlet8() throws Exception {
		FileInputStream filein = new FileInputStream(new File("C:\\Users\\Yash\\Downloads\\bg.jpg"));
		
		MockMultipartFile mockfilein = new MockMultipartFile("profilepic", "bg.jpg", "multipart/form-data",filein);
				
		AddressBean addressBean = new AddressBean("ABC","ahmedabad","gujrat","india","132456");

        ArrayList<AddressBean> addressList = new ArrayList<>();
        addressList.add(addressBean);

        InputStream inputStream = new InputStream() {
            @Override
            public int read() throws IOException {
                return -1;
            }
        };

        UserBean userBean = new UserBean("yash","kakrecha","",1234567890,"yash123","Male","2022-01-01","Movies","A","B","C",addressList,inputStream);
		
		mvc.perform(MockMvcRequestBuilders.fileUpload("/registerServlet")
						.file(mockfilein)
						.contentType(MediaType.MULTIPART_FORM_DATA)
						.accept(MediaType.MULTIPART_FORM_DATA)
						.flashAttr("userBean", userBean)
						.param("password1","fsdhj")
						.param("password2","vnhbm"))
			.andExpect(status().isOk());
	}
	
	@Test
	void testRegisterServlet9() throws Exception {
		
		FileInputStream filein = new FileInputStream(new File("C:\\Users\\Yash\\Downloads\\bg.jpg"));
		
		MockMultipartFile mockfilein = new MockMultipartFile("profilepic", "bg.jpg", "multipart/form-data",filein);
				
//		AddressBean addressBean = new AddressBean("ABC","ahmedabad","gujrat","india","132456");

        ArrayList<AddressBean> addressList = new ArrayList<>();
//        addressList.add(addressBean);

        InputStream inputStream = new InputStream() {
            @Override
            public int read() throws IOException {
                return -1;
            }
        };

        UserBean userBean = new UserBean("yash","kakrecha","yash7890@gmail.com",1234567890,"yash123","Male","2022-01-01","Movies","A","B","C",addressList,inputStream);
		userBean.setType("user");
        
        when(userService.checkEmail(userBean.getEmail())).thenReturn(true);
		
		mvc.perform(MockMvcRequestBuilders.fileUpload("/registerServlet")
						.file(mockfilein)
						.contentType(MediaType.MULTIPART_FORM_DATA)
						.accept(MediaType.MULTIPART_FORM_DATA)
						.flashAttr("userBean", userBean)
						.sessionAttr("user", userBean)
						.param("password1","fsdhj")
						.param("password2","fsdhj"))
			.andExpect(forwardedUrl("register"));
	}

	@Test
	void testRegisterServlet10() throws Exception {
		
		FileInputStream filein = new FileInputStream(new File("C:\\Users\\Yash\\Downloads\\bg.jpg"));
		
		MockMultipartFile mockfilein = new MockMultipartFile("profilepic", "bg.jpg", "multipart/form-data",filein);
				
		AddressBean addressBean = new AddressBean("ABC","ahmedabad","gujrat","india","132456");

        ArrayList<AddressBean> addressList = new ArrayList<>();
        addressList.add(addressBean);

        InputStream inputStream = new InputStream() {
            @Override
            public int read() throws IOException {
                return -1;
            }
        };

        UserBean userBean = new UserBean("yash","kakrecha","yash7890@gmail.com",1234567890,"yash123","Male","2022-01-01","Movies","A","B","C",addressList,inputStream);
		userBean.setType("user");
        
        when(userService.checkEmail(userBean.getEmail())).thenReturn(false);
		
		mvc.perform(MockMvcRequestBuilders.fileUpload("/registerServlet")
						.file(mockfilein)
						.contentType(MediaType.MULTIPART_FORM_DATA)
						.accept(MediaType.MULTIPART_FORM_DATA)
						.flashAttr("userBean", userBean)
						.sessionAttr("user", userBean)
						.param("password1","fsdhj")
						.param("password2","fsdhj"))
			.andExpect(forwardedUrl("register"));
	}
	
	@Test
	void testUpdateServlet1() throws Exception {
		FileInputStream filein = new FileInputStream(new File("C:\\Users\\Yash\\Downloads\\bg.jpg"));
		
		MockMultipartFile mockfilein = new MockMultipartFile("profilepic", "bg.jpg", "multipart/form-data",filein);
				
//		AddressBean addressBean = new AddressBean("ABC","ahmedabad","gujrat","india","132456");
//
        ArrayList<AddressBean> addressList = new ArrayList<>();
//        addressList.add(addressBean);

        InputStream inputStream = new InputStream() {
            @Override
            public int read() throws IOException {
                return -1;
            }
        };

        UserBean userBean = new UserBean("yash","kakrecha","yash7890@gmail.com",1234567890,"yash123","Male","2022-01-01","Movies","A","B","C",addressList,inputStream);
        
        UserBean oldUserBean = new UserBean("yash","kakrecha","yash7890@gmail.com",1234567890,"yash123","Male","2022-01-01","Movies","A","B","C",addressList,inputStream);
        oldUserBean.setType("user");
        
		mvc.perform(MockMvcRequestBuilders.fileUpload("/updateServlet")
						.file(mockfilein)
						.flashAttr("userBean", userBean)
						.sessionAttr("user", oldUserBean)
						.contentType(MediaType.MULTIPART_FORM_DATA)
						.accept(MediaType.MULTIPART_FORM_DATA))
			.andExpect(redirectedUrl("editServlet?email="+userBean.getEmail()));
	}
	
	@Test
	void testUpdateServlet2() throws Exception {
		FileInputStream filein = new FileInputStream(new File("C:\\Users\\Yash\\Downloads\\bg.jpg"));
		
		MockMultipartFile mockfilein = new MockMultipartFile("profilepic", "bg.jpg", "multipart/form-data",filein);
				
		AddressBean addressBean = new AddressBean("ABC","ahmedabad","gujrat","india","132456");

        ArrayList<AddressBean> addressList = new ArrayList<>();
        addressList.add(addressBean);

        InputStream inputStream = new InputStream() {
            @Override
            public int read() throws IOException {
                return -1;
            }
        };

        UserBean userBean = new UserBean("yash","kakrecha","yash7890@gmail.com",1234567890,"yash123","Male","2022-01-01","Movies","A","B","C",addressList,inputStream);
        
        UserBean oldUserBean = new UserBean("yash","kakrecha","yash7890@gmail.com",1234567890,"yash123","Male","2022-01-01","Movies","A","B","C",addressList,inputStream);
        oldUserBean.setType("user");
        
		mvc.perform(MockMvcRequestBuilders.fileUpload("/updateServlet")
						.file(mockfilein)
						.flashAttr("userBean", userBean)
						.sessionAttr("user", oldUserBean)
						.contentType(MediaType.MULTIPART_FORM_DATA)
						.accept(MediaType.MULTIPART_FORM_DATA))
			.andExpect(redirectedUrl("homepage"));
	}
	
	@Test
	void testUpdateServlet3() throws Exception {
		FileInputStream filein = new FileInputStream(new File("C:\\Users\\Yash\\Downloads\\bg.jpg"));
		
		MockMultipartFile mockfilein = new MockMultipartFile("profilepic", "bg.jpg", "multipart/form-data",filein);
				
		AddressBean addressBean = new AddressBean("ABC","ahmedabad","gujrat","india","132456");

        ArrayList<AddressBean> addressList = new ArrayList<>();
        addressList.add(addressBean);

        InputStream inputStream = new InputStream() {
            @Override
            public int read() throws IOException {
                return -1;
            }
        };

        UserBean userBean = new UserBean("yash","kakrecha","yash7890@gmail.com",1234567890,"yash123","Male","2022-01-01","Movies","A","B","C",addressList,inputStream);
        
        UserBean oldUserBean = new UserBean("yash","kakrecha","yash7890@gmail.com",1234567890,"yash123","Male","2022-01-01","Movies","A","B","C",addressList,inputStream);
        oldUserBean.setType("admin");
        
		mvc.perform(MockMvcRequestBuilders.fileUpload("/updateServlet")
						.file(mockfilein)
						.flashAttr("userBean", userBean)
						.sessionAttr("user", oldUserBean)
						.contentType(MediaType.MULTIPART_FORM_DATA)
						.accept(MediaType.MULTIPART_FORM_DATA))
			.andExpect(redirectedUrl("adminServlet"));
	}
	
	@Test
	void testUpdateServlet4() throws Exception {
		FileInputStream filein = new FileInputStream(new File("C:\\Users\\Yash\\Downloads\\bg.jpg"));
		
		MockMultipartFile mockfilein = new MockMultipartFile("profilepic", "bg.jpg", "multipart/form-data",filein);
				
		AddressBean addressBean = new AddressBean("ABC","ahmedabad","gujrat","india","132456");

        ArrayList<AddressBean> addressList = new ArrayList<>();
        addressList.add(addressBean);

        InputStream inputStream = new InputStream() {
            @Override
            public int read() throws IOException {
                return -1;
            }
        };

        UserBean userBean = new UserBean("yash","kakrecha","yash7890@gmail.com",1234567890,"yash123","Male","2022-01-01","Movies","A","B","C",addressList,inputStream);
        
        UserBean oldUserBean = new UserBean("yash","kakrecha","yash7890@gmail.com",1234567890,"yash123","Male","2022-01-01","Movies","A","B","C",addressList,inputStream);
        
		mvc.perform(MockMvcRequestBuilders.fileUpload("/updateServlet")
						.file(mockfilein)
						.flashAttr("userBean", userBean)
						.sessionAttr("user", oldUserBean)
						.contentType(MediaType.MULTIPART_FORM_DATA)
						.accept(MediaType.MULTIPART_FORM_DATA))
			.andExpect(redirectedUrl("index"));
	}
	
	@Test
	void testUpdateServlet5() throws Exception {
		FileInputStream filein = new FileInputStream(new File("C:\\Users\\Yash\\Downloads\\abc.jpg"));
		
		MockMultipartFile mockfilein = new MockMultipartFile("profilepic", "bg.jpg", "multipart/form-data",filein);
				
		AddressBean addressBean = new AddressBean("ABC","ahmedabad","gujrat","india","132456");

        ArrayList<AddressBean> addressList = new ArrayList<>();
        addressList.add(addressBean);

        InputStream inputStream = new InputStream() {
            @Override
            public int read() throws IOException {
                return -1;
            }
        };

        UserBean userBean = new UserBean("yash","kakrecha","yash7890@gmail.com",1234567890,"yash123","Male","2022-01-01","Movies","A","B","C",addressList,inputStream);
             
        when(userService.editProfile("yash7890@gmail.com")).thenReturn(userBean);
        
		mvc.perform(MockMvcRequestBuilders.fileUpload("/updateServlet")
						.file(mockfilein)
						.flashAttr("userBean", userBean)
						.contentType(MediaType.MULTIPART_FORM_DATA)
						.accept(MediaType.MULTIPART_FORM_DATA))
			.andExpect(redirectedUrl("index"));
	}
	
}
