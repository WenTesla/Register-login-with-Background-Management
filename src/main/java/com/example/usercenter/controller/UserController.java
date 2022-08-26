package com.example.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.usercenter.model.domain.User;
import com.example.usercenter.model.domain.request.UserLoginRequest;
import com.example.usercenter.model.domain.request.UserRegisterRequest;
import com.example.usercenter.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.usercenter.contant.UserConstant.ADMIN_ROLE;
import static com.example.usercenter.contant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户接口
 *
 * @author bowen
 */
@RestController //返回类型都是json,适用于编写restful风格的api，返回值默认为json
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public Long userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            return null;
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return null;
        }
        Long id = userService.userRegister(userAccount, userPassword, checkPassword);
        return id;

    }

    @GetMapping("/current")
    public User getCurrentUser(HttpServletRequest request){
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null){
            return null;
        }
        long userId=currentUser.getId();
        //to do校验用户是否合法
        User user = userService.getById(userId);
//        userService.
        return userService.getSafetyUser(user);
    }


    @PostMapping("/login")
    public User userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            return null;
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }
        return userService.userLogin(userAccount, userPassword, request);
    }
    @PostMapping("/logout")
    public Integer userLogout( HttpServletRequest request) {
        if (request == null) {
            return null;
        }

        return userService.userLogout(request);
    }
    @GetMapping("/search")
    public List<User> searchUsers(String username,HttpServletRequest request) {
        if (!isAdmin(request)){
            return new ArrayList<>();
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNoneBlank(username)) {
            queryWrapper.like("username", username);
        }
        //去除
        List<User> userList = userService.list(queryWrapper);
        return userList.stream().map(user ->
            userService.getSafetyUser(user)
        ).collect(Collectors.toList());
    }

    @PostMapping("/delete")
    public boolean deleteUsers(@RequestBody long id,HttpServletRequest request) {
       if (!isAdmin(request)){
           return false;
       }
        if (id <= 0) {
            return false;
        }
        return userService.removeById(id);
    }

    /**
     * 是否为管理员
     * @param request
     * @return
     */
    private boolean isAdmin(HttpServletRequest request){
        // 鉴权,仅管理员查询
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        if (user == null || user.getUserRole() != ADMIN_ROLE){
            return false;
        }
        return true;
    }
}
