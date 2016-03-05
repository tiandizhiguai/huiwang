package com.huiwang.dao;

import com.huiwang.model.UserModel;
import com.huiwang.param.UserParam;

public interface UserDao extends GeneralDao<UserParam, UserModel> {

    public void updateLoginTime();
}
