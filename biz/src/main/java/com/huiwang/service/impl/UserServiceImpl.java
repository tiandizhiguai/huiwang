package com.huiwang.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.huiwang.common.URIUtils;
import com.huiwang.constant.Constants;
import com.huiwang.dao.UserDao;
import com.huiwang.model.UserModel;
import com.huiwang.param.UserParam;
import com.huiwang.service.UserService;
import com.huiwang.vo.User;

@Service
public class UserServiceImpl implements UserService{

    @Resource
    private UserDao userDao;

    @Override
    public List<User> getList(UserParam param) {
        List<User> vos = new ArrayList<User>();

        List<UserModel> models = userDao.getList(param);
        if (CollectionUtils.isEmpty(models)) {
            return vos;
        }

        for (UserModel model : models) {
            vos.add(model2VO(model));
        }
        return vos;
    }

    @Override
    public User get(UserParam param) {
        List<User> vos = getList(param);
        if (!CollectionUtils.isEmpty(vos)) {
            return vos.get(0);
        }
        return null;
    }

    @Override
    public User get(String loginName) {
        UserParam tmpParam = new UserParam();
        tmpParam.setLoginName(loginName);
        List<User> vos = getList(tmpParam);
        if (!CollectionUtils.isEmpty(vos)) {
            return vos.get(0);
        }
        return null;
    }

    @Override
    public void add(UserParam param) {
        param.setPinyinName(com.huiwang.common.StringUtils.chineseToPinyin(param.getRealName()));
        userDao.insert(param);
    }

    @Override
    public void update(UserParam param) {
        UserModel model = userDao.getById(param.getId());
        if (model != null) {
            // 如果用户修改了图片，则删除原来的图片
            if (StringUtils.isNotBlank(param.getPhotoName())) {
                File file = new File(Constants.ADMIN_IMG_ABSOLUTE_PATH + model.getPhotoName());
                if (file.exists()) {
                    file.delete();
                }
            }
            param.setPinyinName(com.huiwang.common.StringUtils.chineseToPinyin(param.getRealName()));
            userDao.update(param);
        }
    }

    @Override
    public void delete(UserParam param) {
        userDao.delete(param);
    }

    public User model2VO(UserModel model) {
        User vo = new User();
        BeanUtils.copyProperties(model, vo);
        String photoUrl = URIUtils.getAdminPhotoFullUrl(model.getPhotoName());
        if (StringUtils.isBlank(photoUrl)) {
            photoUrl = URIUtils.getNoneFullImgUrl();
        }
        vo.setFullPhotoUrl(photoUrl);
        return vo;
    }

    @Override
    public boolean exists(UserParam param) {
        List<UserModel> models = userDao.getList(param);
        if (!CollectionUtils.isEmpty(models)) {
            return true;
        }
        return false;
    }

    public void updateLoginTime(UserParam param) {
        userDao.updateLoginTime(param);
    }

    @Override
    public User get(Long id) {
        UserModel model = userDao.getById(id);
        if (model != null) {
            return model2VO(model);
        }
        return null;
    }
}