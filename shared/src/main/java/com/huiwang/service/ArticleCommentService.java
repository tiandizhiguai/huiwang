package com.huiwang.service;

import com.huiwang.param.ArticleCommentParam;
import com.huiwang.vo.ArticleComment;

public interface ArticleCommentService extends GeneralService<ArticleCommentParam, ArticleComment> {

    int getCount(ArticleCommentParam param);
}
