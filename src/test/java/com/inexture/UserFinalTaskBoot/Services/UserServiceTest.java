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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    void setUp() throws FileNotFoundException{
        this.userService = new UserService(dm,am);
//        dm.save(userBean);
        AddressBean addressBean = new AddressBean("ABC","ahmedabad","gujrat","india","132456");

        ArrayList<AddressBean> addressList = new ArrayList<>();
        addressList.add(addressBean);

        InputStream inputStream = new FileInputStream("C:\\Users\\Yash\\Downloads\\bg.jpg");

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
    void updateUser1() throws FileNotFoundException {

        AddressBean addressBean = new AddressBean("ABC","ahmedabad","gujrat","india","132456");

        ArrayList<AddressBean> addressList = new ArrayList<>();
        addressList.add(addressBean);

        InputStream inputStream = new FileInputStream("C:\\Users\\Yash\\Downloads\\bg.jpg");

        UserBean userBean = new UserBean("yash","kakrecha","yash789@gmail.com",1234567890,"yash123","Male","2022-01-01","Movies","A","B","C",addressList,inputStream);
        
        List<Integer> aids = new ArrayList<Integer>();
        aids.add(1);
        
        when(am.getAids(0)).thenReturn(aids);
        
    	userService.updateUser(userBean);
        verify(dm,atLeastOnce()).save(any());
    }
    
    @Test
    void updateUser2() throws FileNotFoundException {

        AddressBean addressBean = new AddressBean("ABC","ahmedabad","gujrat","india","132456");

        ArrayList<AddressBean> addressList = new ArrayList<>();
        addressList.add(addressBean);

        InputStream inputStream = new FileInputStream("C:\\Users\\Yash\\Downloads\\bg.jpg");

        UserBean userBean = new UserBean("yash","kakrecha","yash789@gmail.com",1234567890,"yash123","Male","2022-01-01","Movies","A","B","C",addressList,inputStream);
        
        List<Integer> aids = new ArrayList<Integer>();
        aids.add(1);
        aids.add(2);
        
        when(am.getAids(0)).thenReturn(aids);
        
    	userService.updateUser(userBean);
        verify(dm,atLeastOnce()).save(any());
    }

    @Test
    void registerUser1() throws FileNotFoundException {
        AddressBean addressBean = new AddressBean("ABC","ahmedabad","gujrat","india","132456");

        ArrayList<AddressBean> addressList = new ArrayList<>();
        addressList.add(addressBean);

        InputStream inputStream = new FileInputStream("C:\\Users\\Yash\\Downloads\\bg.jpg");

        UserBean userBean = new UserBean("yash","kakrecha","yash789@gmail.com",1234567890,"yash123","Male","2022-01-01","Movies","A","B","C",addressList,inputStream);
        
        when(dm.getUid("yash789@gmail.com")).thenReturn(null);
        
    	userService.registerUser(userBean);
        verify(dm,atLeastOnce()).save(any());
    }
    
    @Test
    void registerUser2() throws FileNotFoundException {
    	    	
    	AddressBean addressBean = new AddressBean("ABC","ahmedabad","gujrat","india","132456");

        ArrayList<AddressBean> addressList = new ArrayList<>();
        addressList.add(addressBean);

        InputStream inputStream = new FileInputStream("C:\\Users\\Yash\\Downloads\\bg.jpg");

        UserBean userBean = new UserBean("yash","kakrecha","yash789@gmail.com",1234567890,"yash123","Male","2022-01-01","Movies","A","B","C",addressList,inputStream);
        
        when(dm.getUid("yash789@gmail.com")).thenReturn(1);
        
    	userService.registerUser(userBean);
        verify(dm,never()).save(userBean);
    }
    
    @Test
    void resetPass1() throws FileNotFoundException {
        
    	AddressBean addressBean = new AddressBean("ABC","ahmedabad","gujrat","india","132456");

        ArrayList<AddressBean> addressList = new ArrayList<>();
        addressList.add(addressBean);

        InputStream inputStream = new FileInputStream("C:\\Users\\Yash\\Downloads\\bg.jpg");

        UserBean userBean = new UserBean("yash","kakrecha","yash789@gmail.com",1234567890,"yash123","Male","2022-01-01","Movies","A","B","C",addressList,inputStream);
    	
        Optional<UserBean> opUser = Optional.ofNullable(userBean);
        
    	when(dm.getUid("yash789@gmail.com")).thenReturn(1);
    	when(dm.findById(1)).thenReturn(opUser);
        
        userService.resetPass("yash789@gmail.com","yash123");
        
        verify(dm,atLeastOnce()).save(userBean);
    }
    
    @Test
    void resetPass2() {
        
        UserBean userBean = null;
    	
        Optional<UserBean> opUser = Optional.ofNullable(userBean);
        
    	when(dm.getUid("yash789@gmail.com")).thenReturn(1);
    	when(dm.findById(1)).thenReturn(opUser);
        
        userService.resetPass("yash789@gmail.com","yash123");
        
        verify(dm,never()).save(userBean);
    }
    
    @Test
    void resetPass3() {
        
    	when(dm.getUid("yash789@gmail.com")).thenReturn(0);
    	
        userService.resetPass("yash789@gmail.com","yash123");
        
        verify(dm,never()).save(userBean);
    }

    @Test
    void checkUser1() throws FileNotFoundException {
    	
    	AddressBean addressBean = new AddressBean("ABC","ahmedabad","gujrat","india","132456");

        ArrayList<AddressBean> addressList = new ArrayList<>();
        addressList.add(addressBean);

        InputStream inputStream = new FileInputStream("C:\\Users\\Yash\\Downloads\\bg.jpg");

        UserBean userBean = new UserBean("yash","kakrecha","yash789@gmail.com",1234567890,"yash123","Male","2022-01-01","Movies","A","B","C",addressList,inputStream);
        
        byte[] emptyArray = new byte[0];
        userBean.setImage(emptyArray);
        
    	when(dm.authUser(any(),any())).thenReturn(userBean);
    	
        userService.checkUser("yash789@gmail.com","yash123");
        
    }
    
    @Test
    void checkUser2() {
    	
    	when(dm.authUser(any(),any())).thenReturn(null);
    	
        userService.checkUser("yash789@gmail.com","yash123");
        
    }

    @Test
    void editProfile1() throws FileNotFoundException {
    	
    	AddressBean addressBean = new AddressBean("ABC","ahmedabad","gujrat","india","132456");

        ArrayList<AddressBean> addressList = new ArrayList<>();
        addressList.add(addressBean);

        InputStream inputStream = new FileInputStream("C:\\Users\\Yash\\Downloads\\bg.jpg");

        UserBean userBean = new UserBean("yash","kakrecha","yash789@gmail.com",1234567890,"yash123","Male","2022-01-01","Movies","A","B","C",addressList,inputStream);
    	
        byte[] emptyArray = new byte[0];
        userBean.setImage(emptyArray);
        
        ArrayList<UserBean> userList = new ArrayList<UserBean>();
    	userList.add(userBean);
        
    	when(dm.findByEmail("yash789@gmail.com")).thenReturn(userList);
    	
    	userService.editProfile("yash789@gmail.com");
    }
    
    @Test
    void editProfile2() {
    	
    	ArrayList<UserBean> userList = new ArrayList<UserBean>();
    	
    	when(dm.findByEmail("yash789@gmail.com")).thenReturn(userList);
    	
    	userService.editProfile("yash789@gmail.com");
    }
    
    @Test
    void editProfile3() throws FileNotFoundException {
    	
    	AddressBean addressBean = new AddressBean("ABC","ahmedabad","gujrat","india","132456");

        ArrayList<AddressBean> addressList = new ArrayList<>();
        addressList.add(addressBean);

        InputStream inputStream = new FileInputStream("C:\\Users\\Yash\\Downloads\\bg.jpg");

        UserBean userBean = new UserBean("yash","kakrecha","yash789@gmail.com",1234567890,"yash123","Male","2022-01-01","Movies","A","B","C",addressList,inputStream);
    	
        ArrayList<UserBean> userList = new ArrayList<UserBean>();
    	userList.add(userBean);
        
    	when(dm.findByEmail("yash789@gmail.com")).thenReturn(userList);
    	
    	userService.editProfile("yash789@gmail.com");
    }

    @Test
    void checkEmail1() {
    	when(dm.getUid("yash789@gmail.com")).thenReturn(null);
    	userService.checkEmail("yash789@gmail.com");
    }
    
    @Test
    void checkEmail2() {
    	when(dm.getUid("yash789@gmail.com")).thenReturn(1);
    	userService.checkEmail("yash789@gmail.com");
    }

    @Test
    void findUser() {
    	userService.findUser("yash789@gmail.com","2022-01-01","A","B","C");
    	verify(dm,atLeastOnce()).findUser(any(), any(), any(), any(), any());
    }

    @Test
    void convertToBase64Image() throws FileNotFoundException {
    	InputStream inputStream = new FileInputStream("C:\\Users\\Yash\\Downloads\\bg.jpg");
    	userService.convertToBase64Image(inputStream);
    }
}