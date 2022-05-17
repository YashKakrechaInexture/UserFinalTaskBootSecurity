package com.inexture.UserFinalTaskBoot.Servlets;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
		return "index";
	}
	
	@RequestMapping("/homepage")
	public String homepage(HttpSession session, HttpServletRequest req,HttpServletResponse res) {
		
		res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        res.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        res.setDateHeader("Expires", 0);
		
        session = req.getSession(false);
        
        if(session==null || session.getAttribute("user")==null) {
        	return "index";
        }
        
		return "homepage";
	}
	
	@RequestMapping("/register")
	public String register() {
		return "register";
	}
	
	@RequestMapping("/resetPassword")
	public String resetPassword() {
		return "resetPassword";
	}

	@RequestMapping("/temp")
	public String temp() {
		return "temp";
	}
	
	@RequestMapping("/newPassword")
	public String newPassword(@RequestParam String email,Model model) {
		if(email==null || email.equals("")) {
			model.addAttribute("error", "No user found");
			return "resetPassword";
		}else {
			model.addAttribute("email", email);
			return "newPassword";
		}
	}
	
	@RequestMapping("/loginServlet")
	public String login(HttpServletRequest request,HttpSession session,Model model) {
		
		LOG.debug("Inside LoginServlet");
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");	
		
		LOG.info("Got email and password from login page");

		UserBean u = us.checkUser(email,password);
		
		LOG.debug("Inside LoginServlet : Email and password has been checked.");
		
		if(u != null) {
			
			session=request.getSession();  
			session.setAttribute("user", u);
			
			LOG.debug("Session created and UserBean set to attribute.");
			
			if(u.getType().equals("admin")) {
				
				LOG.info("User is admin, redirecting to admin page.");
				return "redirect:adminServlet";
				
			}else if (u.getType().equals("user")){
				
				LOG.info("User is normal user, redirecting to user home page.");
				return "redirect:homepage";
				
			}else {
				
				LOG.error("User found but its not user or admin");
				return "redirect:index";
			}
			
		}else{
			
			LOG.info("No user found with given email and password, redirecting to login page.");
			model.addAttribute("errormsg","No user found.");
			return "index";
		}
	}
	
	@RequestMapping("/adminServlet")
	public String admin(HttpServletRequest request,HttpSession session,Model model) {
		
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
		return "index";
		
	}
	
	@PostMapping("/authEmailServlet")
	@ResponseBody
	public String authEmailServlet(@RequestParam String email,HttpSession session) {
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
	public String deleteServlet(@RequestParam("uid") String suid,HttpSession session) {
		
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
		
		if(session != null) {
			
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
			ModelAndView model = new ModelAndView("index");
			return model;
		}
	}
	
	@RequestMapping("/newPasswordServlet")
	public String newPasswordServlet(@RequestParam String email,
									@RequestParam String password1,
									@RequestParam String password2,
									Model model,
									HttpSession session) {
		
		LOG.debug("Inside New Password Servlet.");
		
		if(password1==null || password2==null || password1.equals("") || password2.equals("")) {
			return "resetPassword";
		}else {
			if(password1.equals(password2)) {
				
				LOG.debug("Password is same, reseting password.");
				
				us.resetPass(email, password1);
				
				LOG.debug("Redirecting to login page.");
				return "index";
			}else {
				LOG.debug("Password not matched, redirecting to new password page.");
				model.addAttribute("email", email);
				model.addAttribute("error", "Password not matched");
				return "newPassword";
			}
		}
	}

	@RequestMapping("/resetPasswordServlet")
	public ModelAndView resetPasswordServlet(@ModelAttribute UserBean u,
									HttpSession session) {
		
		LOG.debug("Inside Reset Password Servlet.");
		
		LOG.debug("Got data and set in userbean.");
		
		if(us.findUser(u.getEmail(),u.getBirthdate(),u.getQue1(),u.getQue2(),u.getQue3())>0) {
			LOG.debug("User found, redirecting to new password page.");
			
			ModelAndView model = new ModelAndView("redirect:newPassword");
			
			model.addObject("email", u.getEmail());
			
			return model;
		}else {
			LOG.debug("No user found, redirecting to reset password page.");
			ModelAndView model = new ModelAndView("resetPassword");
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
			return "register";
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
				
				return "register";
				
			}else if(!Validation.validate(user)) {
				
				LOG.debug("Validation failed.");
				model.addAttribute("failuser",user);
				model.addAttribute("errormsg","Input Field is empty or too large or type mismatch.");
				
				return "register";
				
			}else if(!us.checkEmail(user.getEmail())) {
				
				LOG.debug("Validation failed.");
				model.addAttribute("failuser",user);
				model.addAttribute("errormsg","Email already exist.");
				
				return "register";
				
			}else if(!password1.equals(password2)) {
				
				LOG.debug("Password not matched.");
				model.addAttribute("failuser",user);
				model.addAttribute("errormsg","Password not matched.");
				
				return "register";
				
			}else {	
				
				LOG.debug("Validation passed, creating user.");
				
				session=request.getSession(false);  
				
				us.registerUser(user);
				
				LOG.debug("User created.");
				
				if(session!=null && session.getAttribute("user")!=null){
					
					LOG.debug("Session is not null");
					
					UserBean olduser = (UserBean)session.getAttribute("user");
					if(olduser.getType().equals("admin")) {
						LOG.debug("Admin is true, redirecting to admin servlet.");
						return "redirect:adminServlet";
					}else if(olduser.getType().equals("user")) {
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
		String fileName = null;
		try {
			if(null!=filePart && filePart.getSize()!=0) {
				fileName = filePart.getName();
				inputstream = filePart.getInputStream();
			}
		} catch (IOException e) {
			LOG.error("Something went wrong! Exception : {}",e);
		}
		
		user.setInputStream(inputstream);
		
		if(!Validation.validate(user)) {
			LOG.debug("Validation failed.");
			model.addAttribute("errormsg","Input Field is empty.");
			
			return "redirect:editServlet?email="+user.getEmail();
			
		}else {
			
			LOG.debug("Validation passed, updating User data.");
			
			session=request.getSession(false);  
			
			if(session!=null) {
				
				LOG.debug("Session is not null, updating user.");
				
				us.updateUser(user,fileName);
				
				UserBean olduser = (UserBean)session.getAttribute("user");
				
				if(olduser.getType().equals("user")) {
					LOG.debug("Session is active and type is user. Redirecting to homepage.");
					return "redirect:homepage";
		        }else if(olduser.getType().equals("admin")){
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
