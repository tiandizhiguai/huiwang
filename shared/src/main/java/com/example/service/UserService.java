package com.example.service;

import com.example.param.UserParam;
import com.example.vo.UserVO;

public interface UserService extends GeneralService<UserParam, UserVO> {

    UserVO get(String loginName);

    boolean exists(UserParam param);

}
