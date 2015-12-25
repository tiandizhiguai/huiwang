package com.example.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.example.dao.TopicDao;
import com.example.model.TopicModel;
import com.example.param.TopicParam;
import com.example.service.TopicService;
import com.example.vo.TopicVO;

@Service
public class TopicServiceImpl implements TopicService {

    @Resource
    private TopicDao topicDao;

    @Override
    public List<TopicVO> getList(TopicParam param) {
        List<TopicVO> vos = new ArrayList<TopicVO>();
        List<TopicModel> models = topicDao.getList(param);

        if (!CollectionUtils.isEmpty(models)) {
            for (TopicModel model : models) {
                vos.add(model2VO(model));
            }
        }

        return vos;
    }

    @Override
    public TopicVO get(TopicParam param) {
        List<TopicVO> vos = getList(param);
        if (!CollectionUtils.isEmpty(vos)) {
            return vos.get(0);
        }
        return null;
    }

    @Override
    public void add(TopicParam param) {
        topicDao.insert(param);
    }

    @Override
    public void update(TopicParam param) {
        TopicModel model = topicDao.getById(param.getId());
        if (model != null) {
            topicDao.update(param);
        } else {
            topicDao.insert(param);
        }
    }

    @Override
    public void delete(TopicParam param) {
        topicDao.delete(param);

    }

    public TopicVO model2VO(TopicModel model) {
        TopicVO vo = new TopicVO();
        BeanUtils.copyProperties(model, vo);
        return vo;
    }

    @Override
    public TopicVO get(Long id) {
        TopicModel model = topicDao.getById(id);
        if (model != null) {
            return model2VO(model);
        }
        return null;
    }
}