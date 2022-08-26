package com.example.usercenter.model.domain.request;

import lombok.Data;

//import java.io.Serial;
import java.io.Serializable;

/**
 * 用户注册请求体
 * @author bowen
 */
@Data
public class UserRegisterRequest implements Serializable {
//    @Serial
//    private static final long serialVersionUID = -543286974259314538L;
    private static final long serialVersionUID = 3191241716373120793L;

    private String userAccount;

    private String userPassword;

    private String checkPassword;

}
