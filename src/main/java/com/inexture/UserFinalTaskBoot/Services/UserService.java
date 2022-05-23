package com.inexture.UserFinalTaskBoot.Services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inexture.UserFinalTaskBoot.Beans.AddressBean;
import com.inexture.UserFinalTaskBoot.Beans.UserBean;
import com.inexture.UserFinalTaskBoot.DAO.AddressDaoInterface;
import com.inexture.UserFinalTaskBoot.DAO.DaoInterface;
import com.inexture.UserFinalTaskBoot.Utilities.ShaEncryption;

/**
 * User service implementation class. It implements all the user related services and calls the dao for CRUD user/address.
 * @author Yash
 *
 */
@Service
public class UserService implements UserInterface{
	static final Logger LOG = Logger.getLogger(UserService.class);
	
	@Autowired
	private DaoInterface dm;
	
	@Autowired
	private AddressDaoInterface am;

	@Autowired
	UserBean user;
	
	@Autowired
	AddressBean address;

	UserService(DaoInterface dm,AddressDaoInterface am){
		this.dm=dm;
		this.am=am;
	}

	@Override
	public List<UserBean> showUsers(String type) {
		
		LOG.debug("Inside ShowUser service.");
		List<UserBean> list = dm.findByType(type);
//		dm.showUserData(type);
		
		LOG.debug("List is returning to Servlet.");
		
		return list;
	}
	
	@Override
	public void deleteUser(int uid) {
		
		LOG.debug("Inside Delete User Service");
		dm.deleteById(uid);
	}
	
	@Override
	public void updateUser(UserBean u) {
		
		LOG.debug("Inside Update Service.");
		
		List<AddressBean> newAddress = u.getAddress();
		
		List<Integer> aids = am.getAids(u.getUid());

		for(AddressBean a:newAddress) {
			if(aids.size()>0) {
				a.setAid(aids.get(0));
				aids.remove(0);
			}
			a.setUser(u);
		}
			
		u.setAddress(newAddress);
		
		u.setType("user");
		
		dm.save(u);
		
//		for(int id : aids) {
////			address.setAid(i);
////			am.delete(address);
//			am.delete(am.getById(id));
//		}
		
//		deletes all addresses
		if(aids.size()>0) {
			LOG.debug("deleting remaining ids"+aids);
			am.deleteAllById(aids);
		}else {
			LOG.debug("No addresses found to delete.");
		}
		
	}
	
	@Override
	public void registerUser(UserBean u) {
		
		LOG.debug("Inside Register Service.");
		
		//checking if user already exist
		if(dm.getUid(u.getEmail())==null) {
			
			LOG.debug("No email found, registering to database.");
			
			//encrypting password
			ShaEncryption sha = new ShaEncryption();
			u.setPassword( sha.passwordEncryption( u.getPassword() ) );
			
			LOG.info("Password Encrypted.");
			
			//adding user in all address
			List<AddressBean> newAddress = u.getAddress();
			
			for(AddressBean a:newAddress) {
				a.setUser(u);
			}
			
			u.setAddress(newAddress);
			
			//setting default type to user
			u.setType("user");
			
			try {
				u.setImage(IOUtils.toByteArray(u.getInputStream()));
			} catch (IOException e) {
				LOG.fatal("Something went wrong! Exception : "+e);
			}
			
			dm.save(u);
			
			LOG.debug("User Registered.");
		
		}else {
			LOG.debug("User Already Exist.");
		}
	}
	
	@Override
	public void resetPass(String email,String password) {
		
		LOG.debug("Inside Reset Password Service.");
		
		//encrypting password
		ShaEncryption sha = new ShaEncryption();
		String encryptedPassword = sha.passwordEncryption(password);
		
		LOG.info("Password Encrypted.");
		
		int uid = dm.getUid(email);
		
		if(uid>0) {
			UserBean u = dm.findById(uid).orElse(null);
			
			if(u!=null) {
				u.setPassword(encryptedPassword);
				System.out.println(u);
				dm.save(u);
				System.out.println(dm.findById(uid));
				
				LOG.info("Password changed.");
			}else {
				LOG.error("No user found with the given uid.");				
			}
		}else {
			LOG.error("No user found with the given email.");
		}
	}
	
	@Override
	public UserBean checkUser(String email,String password) {
		
		//encrypting password
		ShaEncryption sha = new ShaEncryption();
		String encryptedPassword = sha.passwordEncryption(password);
		
		LOG.info("Password Encrypted.");
		
		//checking if user/admin exist
		UserBean u = dm.authUser(email,encryptedPassword);
		
		if(u!=null) {
			u.setInputStream(new ByteArrayInputStream(u.getImage()));
		}
		
		LOG.debug("Checked User in Dao.");
		
		return u;
		
	}
	
	@Override
	public UserBean editProfile(String email) {
		
		LOG.debug("Inside Edit profile service");

		List<UserBean> userList = dm.findByEmail(email);
		
		if(userList.size()==1) {
			UserBean u = userList.get(0);
			
			byte[] initarray = null;
			
			if(u.getImage()!=null) {
				LOG.debug("Image is not null, converting to inputstream.");
				initarray = u.getImage();
			}
			
			u.setInputStream(new ByteArrayInputStream(initarray));
			
			u.setBase64Image( this.convertToBase64Image(u.getInputStream()) );
			
			return u;
			
		}else {
			return null;
		}
	}
	
	@Override
	public boolean checkEmail(String email) {
		LOG.debug("Inside AuthEmail Service.");
		return dm.getUid(email)==null;
	}
	
	@Override
	public int findUser(String email, String birthdate, String ans1, String ans2, String ans3) {
		LOG.debug("Inside FindUser Service.");
		return dm.findUser(email,birthdate,ans1,ans2,ans3);
	}
	
	@Override
	public String convertToBase64Image(InputStream inputStream) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		String base64Image = "";
		try {
			
			byte[] buffer = new byte[4096];
	        int bytesRead = -1;
	         
	        while ((bytesRead = inputStream.read(buffer)) != -1) {
	            outputStream.write(buffer, 0, bytesRead);                  
	        }
	         
	        byte[] imageBytes = outputStream.toByteArray();
	        
	        base64Image = Base64.getEncoder().encodeToString(imageBytes);
	        
	        LOG.debug("Converted image to base64image");
	        
		}catch(Exception e) {
			LOG.fatal("Something went wrong! Exception : "+e);
		}finally {
			
			try {
				
				if(inputStream != null) {
					LOG.debug("InputStream closed.");
					inputStream.close();    
				}
				if(outputStream != null) {
					LOG.debug("OutputStream closed.");
					outputStream.close();    
				}

			}catch(Exception ex) {
				LOG.fatal("Something went wrong! Exception : "+ex);
			}
		}
		return base64Image;
	}
}
