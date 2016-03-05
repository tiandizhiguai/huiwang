package com.huiwang.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.huiwang.dao.UserIdeaDao;
import com.huiwang.param.UserIdeaParam;
import com.huiwang.service.UserIdeaService;
import com.huiwang.vo.UserIdea;

@Service
public class UserIdeaServiceImpl implements UserIdeaService {

    @Resource
    private UserIdeaDao userIdeaDao;

    @Override
    public List<UserIdea> getList(UserIdeaParam param) {
        return null;
    }

    @Override
    public UserIdea get(UserIdeaParam param) {
        return null;
    }

    @Override
    public UserIdea get(Long id) {
        return null;
    }

    @Override
    public void add(UserIdeaParam param) {
        userIdeaDao.insert(param);
    }

    @Override
    public void update(UserIdeaParam param) {

    }

    @Override
    public void delete(UserIdeaParam param) {

    }
}