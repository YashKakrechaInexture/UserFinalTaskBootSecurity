package com.inexture.UserFinalTaskBoot.DAO;

import com.inexture.UserFinalTaskBoot.Beans.UserBean;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DaoInterfaceTest {

    @Autowired
    DaoInterface daoInterface;

    @Test
    void findByType() {

        List<UserBean> list = daoInterface.findByType("user");

        assertNotEquals(list,null);

    }

    @Test
    void getUid() {
        Integer actualResult1 = daoInterface.getUid("aghbj@gmail.com");
        Integer actualResult2 = daoInterface.getUid("abc@gmail.com");

        assertNull(actualResult1);
        assertEquals(9,actualResult2);
    }

    @Test
    void authUser() {
        UserBean userBean1 = daoInterface.authUser("abc@gmail.com","abc123");
        UserBean userBean2 = daoInterface.authUser("abc@gmail.com","ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad");

        assertNull(userBean1);
        assertNotEquals(null,userBean2);
    }

    @Test
    void findUser() {
        Integer uid1 = daoInterface.findUser("scdfd@gmail.com","2022-03-28","A","B","C");
        Integer uid2 = daoInterface.findUser("dummy13@gmail.com","2022-03-28","A","B","C");

        assertNull(uid1);
        assertEquals(24,uid2);
    }
}