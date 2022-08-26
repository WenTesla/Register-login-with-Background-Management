package com.example.usercenter.service;

import com.example.usercenter.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class UserServiceTest {
    @Resource
    private UserService userService;

    @Test
    public void testAddUser() {
        User user = new User();
        user.setUsername("bowen");
        user.setUserAccount("123");
        user.setAvatarUrl("");
        user.setGender(0);
        user.setUserPassword("xxx");
        user.setPhone("123");
        user.setEmail("456");
        boolean result = userService.save(user);
        System.out.println(result);
        Assertions.assertEquals(true, result);
    }

    @Test
    void userRegister() {
        String userAccount = "yupi";
        String userPassword = "";
        String checkpassword = "123456";
        Long result = userService.userRegister(userAccount, userPassword, checkpassword);
        Assertions.assertEquals(-1, result);
        userAccount = "12";
        result = userService.userRegister(userAccount, userPassword, checkpassword);
        Assertions.assertEquals(-1, result);
        userAccount = "yupi";
        userPassword = "123456";
        result = userService.userRegister(userAccount, userPassword, checkpassword);
        Assertions.assertEquals(-1, result);
        userAccount = "yu pi";
        userPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkpassword);
        Assertions.assertEquals(-1, result);
        checkpassword = "123456789";
        result = userService.userRegister(userAccount, userPassword, checkpassword);
        Assertions.assertEquals(-1, result);
        userAccount = "dogyupi";
        checkpassword = "123456789";
        result = userService.userRegister(userAccount, userPassword, checkpassword);
        Assertions.assertEquals(-1, result);
        userAccount = "yupi";
        userPassword = "123456789";
        result = userService.userRegister(userAccount, userPassword, checkpassword);
        Assertions.assertTrue(result > 0);

    }

}