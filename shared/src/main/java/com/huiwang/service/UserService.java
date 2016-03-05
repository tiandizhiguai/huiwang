package com.huiwang.service;

import com.huiwang.param.UserParam;
import com.huiwang.vo.User;

public interface UserService extends GeneralService<UserParam, User> {

    User get(String loginName);

    boolean exists(UserParam param);

}
