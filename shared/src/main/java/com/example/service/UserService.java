package com.example.service;

import com.example.param.UserParam;
import com.example.vo.User;

public interface UserService extends GeneralService<UserParam, User> {

    User get(String loginName);

    boolean exists(UserParam param);

}
