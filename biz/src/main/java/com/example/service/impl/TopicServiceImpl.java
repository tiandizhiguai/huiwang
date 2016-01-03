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
import com.example.vo.Topic;

@Service
public class TopicServiceImpl implements TopicService {

    @Resource
    private TopicDao topicDao;

    @Override
    public List<Topic> getList(TopicParam param) {
        List<Topic> vos = new ArrayList<Topic>();
        List<TopicModel> models = topicDao.getList(param);

        if (!CollectionUtils.isEmpty(models)) {
            for (TopicModel model : models) {
                vos.add(model2VO(model));
            }
        }

        return vos;
    }

    @Override
    public Topic get(TopicParam param) {
        List<Topic> vos = getList(param);
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
        topicDao.update(param);
    }

    @Override
    public void delete(TopicParam param) {
        topicDao.delete(param);
    }

    public Topic model2VO(TopicModel model) {
        Topic vo = new Topic();
        BeanUtils.copyProperties(model, vo);
        return vo;
    }

    @Override
    public Topic get(Long id) {
        TopicModel model = topicDao.getById(id);
        if (model != null) {
            return model2VO(model);
        }
        return null;
    }

    public String getTopicName(Long id) {
        TopicParam topicParam = new TopicParam();
        topicParam.setId(id);
        List<TopicModel> topics = topicDao.getList(topicParam);
        if (CollectionUtils.isEmpty(topics)) {
            return null;
        }
        return topics.get(0).getName();
    }
}