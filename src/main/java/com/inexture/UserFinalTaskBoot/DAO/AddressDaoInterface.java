package com.inexture.UserFinalTaskBoot.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.inexture.UserFinalTaskBoot.Beans.AddressBean;

/**
 * Address Dao Interface. Stores the methods list related to create, update, read, delete address's data from database table.
 * @author Yash
 *
 */
public interface AddressDaoInterface extends JpaRepository<AddressBean, Integer>{

	/**
	 * It returns address id's array list attached with the given user id from database table.
	 * @param uid - user id in table
	 * @return List<Integer> - List of aid from address table
	 */
	@Query("select aid from AddressBean where uid=?1")
	List<Integer> getAids(int uid);
	
}
