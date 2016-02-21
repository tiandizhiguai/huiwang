package com.example.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.example.dao.UserIdeaDao;
import com.example.param.UserIdeaParam;
import com.example.service.UserIdeaService;
import com.example.vo.UserIdea;

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