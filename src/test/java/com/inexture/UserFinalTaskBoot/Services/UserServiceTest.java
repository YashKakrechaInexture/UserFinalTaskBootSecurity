package com.inexture.UserFinalTaskBoot.Services;

import com.inexture.UserFinalTaskBoot.Beans.AddressBean;
import com.inexture.UserFinalTaskBoot.Beans.UserBean;
import com.inexture.UserFinalTaskBoot.DAO.AddressDaoInterface;
import com.inexture.UserFinalTaskBoot.DAO.DaoInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private DaoInterface dm;

    @Mock
    private AddressDaoInterface am;

    private UserService userService;

    @BeforeEach
    void setUp(){
        this.userService = new UserService(dm,am);
    }

    @Test
    void showUsers() {
        userService.showUsers("user");
        verify(dm).findByType("user");
    }

    @Test
    void deleteUser() {
        userService.deleteUser(7);
        verify(dm).deleteById(7);
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
        userService.updateUser(userBean,"abc");
        verify(dm).save(userBean);
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

        userService.registerUser(userBean);
        verify(dm).getUid("yash789@gmail.com");
    }

    @Test
    void resetPass() {
        userService.resetPass("yash789@gmail.com","yash123");
    }

    @Test
    void checkUser() {
        userService.checkUser("yash789@gmail.com","yash123");
//        verify(dm)
    }

    @Test
    void editProfile() {
    }

    @Test
    void checkEmail() {
    }

    @Test
    void findUser() {
    }

    @Test
    void convertToBase64Image() {
    }
}