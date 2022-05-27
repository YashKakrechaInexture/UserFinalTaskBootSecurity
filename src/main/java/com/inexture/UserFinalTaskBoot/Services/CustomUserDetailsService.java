package com.inexture.UserFinalTaskBoot.Services;

import com.inexture.UserFinalTaskBoot.Beans.CustomUserDetails;
import com.inexture.UserFinalTaskBoot.Beans.UserBean;
import com.inexture.UserFinalTaskBoot.DAO.DaoInterface;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    static final Logger LOG = Logger.getLogger(UserService.class);
    @Autowired
    private DaoInterface dm;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        LOG.debug("Inside loadUser by email method.");

        List<UserBean> userBeanList = dm.findByEmail(email);
        UserBean userBean = null;

        if(userBeanList.size()==1){
            LOG.debug("User found from database.");
            userBean = userBeanList.get(0);
        }else {
            LOG.debug("No user found! Throwing exception.");
            throw new UsernameNotFoundException("No user found!");
        }
        return new CustomUserDetails(userBean);
    }
}
