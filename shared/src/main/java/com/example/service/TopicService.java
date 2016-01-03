package com.example.service;

import com.example.param.TopicParam;
import com.example.vo.Topic;

public interface TopicService extends GeneralService<TopicParam, Topic> {

    String getTopicName(Long id);
}
