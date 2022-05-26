package com.inexture.UserFinalTaskBoot.Services;

import java.io.InputStream;
import java.util.List;

import com.inexture.UserFinalTaskBoot.Beans.UserBean;

/**
 * This is User service interface. It stores the logic methods list related to add, delete, update user related data
 * in the database table.
 * @author Yash
 *
 */
public interface UserInterface {
	
	/**
	 * It calls the User's list dao based on the given type from database table.
	 * @param type - type of user (admin,user)
	 * @return arraylist - arraylist of the user bean object
	 */
	List<UserBean> showUsers(String type);
	
	/**
	 * It calls the register dao method and registers user in database table.
	 * @param u - user bean object
	 */
	void registerUser(UserBean u);
	
	/**
	 * It encrypts the password and calls the reset password method from dao and resets the 
	 * password in the given email address in the database table
	 * @param email - email address of the user
	 * @param password - password of user
	 */
	void resetPass(String email,String password);
	
	/**
	 * It calls the update user, update image, update address method of dao, and it updates the
	 * all the user data in the database table.
	 * @param u - user bean object
	 */
	void updateUser(UserBean u);
	
	/**
	 * It calls to delete user dao method, and it deletes the user based on given user id.
	 * @param uid - user id from database table
	 */
	void deleteUser(int uid);
	
	/**
	 * It checks the email and password exist in database table by calling dao method. It returns whatever returned by dao.
	 * Dao returns either user object if user found and null if no users found.
	 * @param email - email address of user
	 * @param password - password of user
	 * @return Userbean - user bean object<br>
	 * 			null - if no users found
	 */
	UserBean checkUser(String email,String password);
	
	/**
	 * It gets the all the user data and address data from dao, based on given email
	 * @param email - email address of user
	 */
	UserBean editProfile(String email);
	
	/**
	 * It checks if email is already exist in the database table or not.
	 * @param email - email of the user
	 * @return true - If no user found in mysql database table.<br>
	 * 		   false - If user found in mysql database table.
	 */
	boolean checkEmail(String email);
	
	/**
	 * It finds the user with given email, birthdate and security answers from dao for forgot password.
	 * @param email - email of the user
	 * @param birthdate - birthdate of the user
	 * @param ans1 - security ans1 of the user
	 * @param ans2 - security ans2 of the user
	 * @param ans3 - security ans3 of the user
	 * @return uid - User id from table
	 */
	int findUser(String email, String birthdate, String ans1, String ans2, String ans3);
	
	/**
	 * This method converts input stream image to base64 image. It accepts UserBean object with input stream variable filled.
	 * @param inputStream - input stream of image
	 */
	String convertToBase64Image(InputStream inputStream);
}
