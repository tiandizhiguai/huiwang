package com.huiwang.dao;

import com.huiwang.model.CommentMessageModel;
import com.huiwang.param.CommentMessageParam;

public interface CommentMessageDao extends GeneralDao<CommentMessageParam, CommentMessageModel> {

    int getCount(CommentMessageParam param);
}
