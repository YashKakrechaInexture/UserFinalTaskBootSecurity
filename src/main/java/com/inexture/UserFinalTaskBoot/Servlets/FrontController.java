package com.inexture.UserFinalTaskBoot.Servlets;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.inexture.UserFinalTaskBoot.Beans.UserBean;
import com.inexture.UserFinalTaskBoot.Services.UserInterface;
import com.inexture.UserFinalTaskBoot.Utilities.Validation;

@Controller
public class FrontController {
	
	static final Logger LOG = Logger.getLogger(FrontController.class);
	
	@Autowired
	private UserInterface us;
	
	@RequestMapping({"/","/index"})
	public String index() {
		return "indexView";
	}
	
	@RequestMapping("/homepage")
	public String homepage() {
		return "homepageView";
	}
	
	@RequestMapping("/register")
	public String register() {
		return "registerView";
	}
	
	@RequestMapping("/resetPassword")
	public String resetPassword() {
		return "resetPasswordView";
	}

	@RequestMapping("/temp")
	public String temp() {
		return "tempView";
	}
	
	@RequestMapping("/newPassword")
	public String newPassword(@RequestParam String email,Model model) {
		if(email==null || email.equals("")) {
			model.addAttribute("error", "No user found");
			return "resetPasswordView";
		}else {
			model.addAttribute("email", email);
			return "newPasswordView";
		}
	}
	
	@RequestMapping("/loginServlet")
	public String login(HttpServletRequest request,HttpSession session,@RequestParam String email,@RequestParam String password,Model model) {
		
		LOG.debug("Inside LoginServlet");
		
//		String email = request.getParameter("email");
//		String password = request.getParameter("password");
		
		LOG.info("Got email and password from login page");

		UserBean u = us.checkUser(email,password);
		
		LOG.debug("Inside LoginServlet : Email and password has been checked.");
		
		if(u != null) {

			session = request.getSession(false);
			session.setAttribute("user", u);
			
			LOG.debug("Session created and UserBean set to attribute.");
			
			if(u.getType()!=null && u.getType().equals("admin")) {
				
				LOG.info("User is admin, redirecting to admin page.");
				return "redirect:adminServlet";
				
			}else if (u.getType()!=null && u.getType().equals("user")){
				
				LOG.info("User is normal user, redirecting to user home page.");
				return "redirect:homepage";
				
			}else {
				
				LOG.error("User found but its not user or admin");
				return "redirect:index";
			}
			
		}else{
			
			LOG.info("No user found with given email and password, redirecting to login page.");
			model.addAttribute("errormsg","No user found.");
			return "indexView";
		}
	}
	
	@RequestMapping("/adminServlet")
	public String admin(Model model) {
		
		LOG.info("Inside Admin Servlet.");
		
		LOG.debug("Adding User list to request attribute.");
		
		model.addAttribute("data", us.showUsers("user"));
		
		LOG.debug("Redirecting to Admin page.");
		
		return "admin";
	}
	
	@RequestMapping("/logoutServlet")
	public String logout(HttpServletRequest request,HttpSession session) {
		
		LOG.debug("Inside Logout Servlet.");
		
		session=request.getSession(false);  
		
		if(session!=null) {
			LOG.debug("Session is not null, invalidating it.");
			session.invalidate();
		}
		
		LOG.debug("Redirecting to login page.");
		return "indexView";
		
	}
	
	@PostMapping("/authEmailServlet")
	@ResponseBody
	public String authEmailServlet(@RequestParam String email) {
		LOG.debug("Inside Auth email servlet.");
		
		if(!us.checkEmail(email)) {
			LOG.info("Email exist in table.");
			return "<span style=\"color:red;\">Email Already Taken.</span>";
		}else {
			LOG.info("Email does not exist in table.");
			return "<span style=\"color:green;\">Email Available.</span>";
		}
	}
	
	@RequestMapping("/deleteServlet")
	public String deleteServlet(@RequestParam("uid") String suid) {
		
		LOG.debug("Inside Delete Servlet.");
		
		int uid = Integer.parseInt(suid);
		
		LOG.debug("User deleting service calling.");
		
		us.deleteUser(uid);
		
		LOG.debug("User deleted, redirecting to admin servlet.");
		
		return "redirect:adminServlet";
		
	}
	
	@RequestMapping("/editServlet")
	public ModelAndView editServlet(@RequestParam String email,HttpSession session) {
		LOG.debug("Inside Edit Servlet.");
		
		if(session != null && session.getAttribute("user")!=null) {
			
			LOG.debug("Session not null.");
			
			LOG.debug("Get email.");

			UserBean u = us.editProfile(email);
			
			LOG.debug("Setting user bean to request attribute.");
			
			ModelAndView model = new ModelAndView("register");
			model.addObject("user", u);
	        
	        LOG.debug("Redirecting to edit jsp page.");
			
	        return model;
		}else {
			LOG.debug("Session is null, redirecting to login page.");
			ModelAndView model = new ModelAndView("indexView");
			return model;
		}
	}
	
	@RequestMapping("/newPasswordServlet")
	public String newPasswordServlet(@RequestParam String email,
									@RequestParam String password1,
									@RequestParam String password2,
									Model model) {
		
		LOG.debug("Inside New Password Servlet.");
		
		if(password1==null || password2==null || password1.equals("") || password2.equals("")) {
			return "resetPasswordView";
		}else {
			if(password1.equals(password2)) {
				
				LOG.debug("Password is same, resetting password.");
				
				us.resetPass(email, password1);
				
				LOG.debug("Redirecting to login page.");
				return "indexView";
			}else {
				LOG.debug("Password not matched, redirecting to new password page.");
				model.addAttribute("email", email);
				model.addAttribute("error", "Password not matched");
				return "newPasswordView";
			}
		}
	}

	@RequestMapping("/resetPasswordServlet")
	public ModelAndView resetPasswordServlet(@ModelAttribute UserBean u,
									HttpSession session) {
		
		LOG.debug("Inside Reset Password Servlet.");
		
		LOG.debug("Got data and set in user bean.");
		
		if(us.findUser(u.getEmail(),u.getBirthdate(),u.getQue1(),u.getQue2(),u.getQue3())>0) {
			LOG.debug("User found, redirecting to new password page.");
			
			ModelAndView model = new ModelAndView("redirect:newPassword");
			
			model.addObject("email", u.getEmail());
			
			return model;
		}else {
			LOG.debug("No user found, redirecting to reset password page.");
			ModelAndView model = new ModelAndView("resetPasswordView");
			model.addObject("error", "No user found");
			return model;
		}
		
	}
	
	@PostMapping(path="/registerServlet",consumes= {MediaType.MULTIPART_FORM_DATA_VALUE})
	public String registerServlet(@RequestParam String password1,
									@RequestParam String password2,
									@RequestParam(name="profilepic",required=false) MultipartFile filePart,
									@Valid @ModelAttribute UserBean user,
									BindingResult br,
									HttpSession session,
									HttpServletRequest request,
									Model model) {
		
		LOG.debug("Inside Register Servlet.");
		
		if(br.hasErrors()) {
			
			LOG.debug("Inside has error.");
			
			List<FieldError> error = br.getFieldErrors();
			List<String> errors = new ArrayList<>();
			for(FieldError f : error) {
				errors.add(f.getDefaultMessage());
			}
			model.addAttribute("validerror",errors);
			return "registerView";
		}else {
			
			LOG.debug("User validated.");
				
			InputStream inputStream = null;
			try {
				if(null!=filePart && filePart.getSize()!=0) {
					inputStream = filePart.getInputStream();
				}
			}catch(Exception e) {
				LOG.error("Something went wrong! Exception : {}",e);
			}
			
			LOG.debug("Got all the data from register page.");
			
			user.setPassword(password1);
			user.setInputStream(inputStream);
			
			if(null==filePart || filePart.getSize()==0){
	
				LOG.debug("Image empty.");
				model.addAttribute("failuser",user);
				model.addAttribute("errormsg","Image is empty.");
				
				return "registerView";
				
			}else if(!Validation.validate(user)) {
				
				LOG.debug("Validation failed.");
				model.addAttribute("failuser",user);
				model.addAttribute("errormsg","Input Field is empty or too large or type mismatch.");
				
				return "registerView";
				
			}else if(!password1.equals(password2)) {
				
				LOG.debug("Password not matched.");
				model.addAttribute("failuser",user);
				model.addAttribute("errormsg","Password not matched.");
				
				return "registerView";
				
			}else if(!us.checkEmail(user.getEmail())) {
				
				LOG.debug("Validation failed.");
				model.addAttribute("failuser",user);
				model.addAttribute("errormsg","Email already exist.");
				
				return "registerView";
				
			}else {	
				
				LOG.debug("Validation passed, creating user.");
				
				session=request.getSession(false);  
				
				us.registerUser(user);
				
				LOG.debug("User created.");
				
				if(session!=null && session.getAttribute("user")!=null){
					
					LOG.debug("Session is not null");
					
					UserBean olduser = (UserBean)session.getAttribute("user");
					if(olduser.getType()!=null && olduser.getType().equals("admin")) {
						LOG.debug("Admin is true, redirecting to admin servlet.");
						return "redirect:adminServlet";
					}else if(olduser.getType()!=null && olduser.getType().equals("user")) {
						LOG.debug("User session is active, redirecting to homepage.");
						return "redirect:homepage";
					}else {
						LOG.error("Session is active but not user or admin found.");
						return "redirect:register";
					}
				}else {
					LOG.debug("Session is null, redirecting to login page.");
					return "redirect:index";
				}
				
			}
		}
	}
	
	@PostMapping(path="/updateServlet",consumes= {MediaType.MULTIPART_FORM_DATA_VALUE})
	public String updateServlet(@RequestParam(name="profilepic",required=false) MultipartFile filePart,
									@ModelAttribute UserBean user,
									HttpSession session,
									HttpServletRequest request,
									Model model) {
		
		LOG.debug("Inside Update Servlet.");
		
		InputStream inputstream = null;
		try {
			if(null!=filePart && filePart.getSize()!=0) {
				LOG.debug("Image is provided.");
				inputstream = filePart.getInputStream();
				user.setInputStream(inputstream);
				try {
					user.setImage(IOUtils.toByteArray(user.getInputStream()));
				} catch (IOException e) {
					LOG.fatal("Something went wrong! Exception : "+e);
				}
				
			}else {
				LOG.debug("Image is not provided, getting old image from dao.");
				UserBean olduser = us.editProfile(user.getEmail());
				inputstream = olduser.getInputStream();
				user.setImage(olduser.getImage());
			}
		} catch (IOException e) {
			LOG.error("Something went wrong! Exception : {}",e);
		}
		
		if(!Validation.validate(user)) {
			LOG.debug("Validation failed.");
			model.addAttribute("errormsg","Input Field is empty.");
			
			return "redirect:editServlet?email="+user.getEmail();
			
		}else {
			
			LOG.debug("Validation passed, updating User data.");
			
			session=request.getSession(false);  
			
			if(session!=null && session.getAttribute("user")!=null) {
				
				LOG.debug("Session is not null, updating user.");
				
				us.updateUser(user);
				
				UserBean olduser = (UserBean)session.getAttribute("user");
				
				if(olduser.getType()!=null && olduser.getType().equals("user")) {
					LOG.debug("Session is active and type is user. Redirecting to homepage.");
					return "redirect:homepage";
		        }else if(olduser.getType()!=null && olduser.getType().equals("admin")){
		        	LOG.debug("Session is active and type is admin. Redirecting to admin servlet.");
					return "redirect:adminServlet";
		        }else {
		        	LOG.error("Session is active but no user or admin found.");
		        	return "redirect:index";
		        }
			}else {
				LOG.warn("Session is null, redirecting to login page.");
				return "redirect:index";
			}
		}
	}
}
