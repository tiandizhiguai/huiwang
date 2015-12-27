package com.example.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.example.common.URIUtils;
import com.example.constant.Constants;
import com.example.dao.UserDao;
import com.example.model.UserModel;
import com.example.param.UserParam;
import com.example.service.UserService;
import com.example.vo.UserVO;

@Service
public class UserServiceImpl implements UserService{

    @Resource
    private UserDao userDao;

    @Override
    public List<UserVO> getList(UserParam param) {
        List<UserVO> vos = new ArrayList<UserVO>();
        List<UserModel> models = userDao.getList(param);

        if (!CollectionUtils.isEmpty(models)) {
            for (UserModel model : models) {
                vos.add(model2VO(model));
            }
        }

        return vos;
    }

    @Override
    public UserVO get(UserParam param) {
        List<UserVO> vos = getList(param);
        if (!CollectionUtils.isEmpty(vos)) {
            return vos.get(0);
        }
        return null;
    }

    @Override
    public UserVO get(String loginName) {
        UserParam tmpParam = new UserParam();
        tmpParam.setLoginName(loginName);
        List<UserVO> vos = getList(tmpParam);
        if (!CollectionUtils.isEmpty(vos)) {
            return vos.get(0);
        }
        return null;
    }

    @Override
    public void add(UserParam param) {
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

            userDao.update(param);
        }
    }

    @Override
    public void delete(UserParam param) {
        userDao.delete(param);

    }

    public UserVO model2VO(UserModel model) {
        UserVO vo = new UserVO();
        BeanUtils.copyProperties(model, vo);
        String photoUrl = URIUtils.getAdminPhotoFullUrl(model.getPhotoName());
        if (StringUtils.isBlank(photoUrl)) {
            photoUrl = URIUtils.getNoneFullImgUrl();
        }
        vo.setPhotoFullUrl(photoUrl);
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

    @Override
    public UserVO get(Long id) {
        UserModel model = userDao.getById(id);
        if (model != null) {
            return model2VO(model);
        }
        return null;
    }
}