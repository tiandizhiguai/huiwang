package com.huiwang.service;

import com.huiwang.param.CommentMessageParam;
import com.huiwang.vo.CommentMessage;

public interface CommentMessageService extends GeneralService<CommentMessageParam, CommentMessage> {

    int getCount(CommentMessageParam param);
}
