package com.example.usercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.usercenter.model.domain.User;

import javax.servlet.http.HttpServletRequest;

/**
 * @author WenTesla
 * @description 针对表【user(用户)】的数据库操作Service
 * 用户服务
 * @createDate 2022-08-23 17:52:23
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return 新用户id
     */
    Long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 用户登录
     * @param userAccount
     * @param userPassword
     * @return 返回脱敏后的用户信息
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户脱敏
     * @param originUser
     * @return
     */
    User getSafetyUser(User originUser);

    /**
     * 请求用户注销
     *
     * @param request
     * @return
     */
    int userLogout(HttpServletRequest request);
}
