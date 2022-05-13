package com.inexture.UserFinalTaskBoot.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.inexture.UserFinalTaskBoot.Beans.UserBean;


/**
 * User Dao Interface. Stores the methods list related to create, update, read, delete user's data from database table.
 * @author Yash
 *
 */
public interface DaoInterface extends JpaRepository<UserBean, Integer>{
	
	
	List<UserBean> findByType(String type);
	/**
	 * It returns user id from the table related to the email given, if no user found then returns 0.
	 * @param email - Stores email of user
	 * @return uid - User id from user database table<br>
	 * 		   0 - If no user is found with the given email
	 */
	@Query("select uid from UserBean where email=?1")
	public int getUid(String email);
	
	/**
	 * This method checks if user is present in database table or not based on given email, password. 
	 * If User is present then it returns user bean object filled with all the details of user.
	 * Else it returns null.
	 * @param email - email of user
	 * @param password - encrypted password of user
	 * @return u - User bean object
	 * 		   null - if no user found in table
	 */
	@Query("from UserBean where email=?1 and password=?2")
	public UserBean authUser(String email,String password);
	
	/**
	 * It accepts empty arraylist and type of user and fills the arraylist with user bean objects from database table based on given type of user. 
	 * @param type - type of user. Exa. admin, user.
	 * @return list - list of users
	 */
//	public List<UserBean> showUserData(String type);
	
	/**
	 * This method finds user based on given email, birthdate and security answers in forgot password.
	 * @param u - user bean objects
	 * @return false - If user found in database table.<br>
	 * 		   true - If no user found with given information in database table.
	 */
	@Query("select uid from UserBean where email=?1 and birthdate=?2 and ans1=?3 and ans2=?4 and ans3=?5")
	public int findUser(String email, String birthdate, String ans1, String ans2, String ans3);
	
}
