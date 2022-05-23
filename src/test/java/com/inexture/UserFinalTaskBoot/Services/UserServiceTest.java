package com.inexture.UserFinalTaskBoot.Services;

import com.inexture.UserFinalTaskBoot.Beans.AddressBean;
import com.inexture.UserFinalTaskBoot.Beans.UserBean;
import com.inexture.UserFinalTaskBoot.DAO.AddressDaoInterface;
import com.inexture.UserFinalTaskBoot.DAO.DaoInterface;
import com.inexture.UserFinalTaskBoot.Utilities.ShaEncryption;

//import uk.co.jemos.podam.api.PodamFactory;
//import uk.co.jemos.podam.api.PodamFactoryImpl;

//import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
//import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

//import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
//import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
//import static org.mockito.Mockito.atMostOnce;
//import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private DaoInterface dm;

    @Mock
    private AddressDaoInterface am;
    
    @Mock
    ShaEncryption sha;

    @InjectMocks
    private UserService userService;
    
    @InjectMocks
    UserBean userBean;
    
    @Mock
    UserBean newUserBean;
    
    @BeforeEach
    void setUp(){
        this.userService = new UserService(dm,am);
//        dm.save(userBean);
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
        
    	dm.save(userBean);
//    	when(dm.save(any(UserBean.class))).thenReturn(userBean);
    }
    
    @Test
    void showUsers() {
        userService.showUsers("user");
        verify(dm,atLeastOnce()).findByType("user");	
    }

    @Test
    void deleteUser() {
        userService.deleteUser(7);
        verify(dm,atLeastOnce()).deleteById(7);
    }

    @Test
    void updateUser() {

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
        
    	userService.updateUser(userBean);
        verify(dm,atLeastOnce()).save(any());
    }

    @Test
    void registerUser() {
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
//    	System.out.println(userBean.getEmail());
//    	userBean.setEmail("abc");
    	userService.registerUser(userBean);
        verify(dm,atLeastOnce()).save(any());
    }
    
    @Test
    void notRegisterUser() {
    	
    	System.out.println(dm.findByEmail("yash7890@gmail.com"));
    	
    	AddressBean addressBean = new AddressBean("ABC","ahmedabad","gujrat","india","132456");

        ArrayList<AddressBean> addressList = new ArrayList<>();
        addressList.add(addressBean);

        InputStream inputStream = new InputStream() {
            @Override
            public int read() throws IOException {
                return -1;
            }
        };

        UserBean userBean = new UserBean("yash","kakrecha",null,1234567890,"yash123","Male","2022-01-01","Movies","A","B","C",addressList,inputStream);
        
    	userService.registerUser(userBean);
    	
        verify(dm,never()).save(userBean);
    }
    
    @Test
    void resetPass() {
        userService.resetPass("yash789@gmail.com","yash123");
    }

    @Test
    void checkUser() {
        userService.checkUser("yash789@gmail.com","yash123");
        verify(dm,atLeastOnce()).authUser(any(),any());
    }

    @Test
    void editProfile() {
    	userService.editProfile("yash789@gmail.com");
    }

    @Test
    void checkEmail() {
    	userService.checkEmail("yash789@gmail.com");
    	System.out.println(dm.getUid("yash789@gmail.com")==null);
    }

    @Test
    void findUser() {
    	userService.findUser("yash789@gmail.com","2022-01-01","A","B","C");
    	verify(dm,atLeastOnce()).findUser(any(), any(), any(), any(), any());
    }

    @Test
    void convertToBase64Image() {
    	InputStream inputStream = new InputStream() {
            @Override
            public int read() throws IOException {
                return -1;
            }
        };
    	userService.convertToBase64Image(inputStream);
    }
}