package com.huiwang.service;

import com.huiwang.param.TopicParam;
import com.huiwang.vo.Topic;

public interface TopicService extends GeneralService<TopicParam, Topic> {

    String getTopicName(Long id);
}
