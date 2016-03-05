package com.huiwang.dao;

import com.huiwang.model.ArticleCommentModel;
import com.huiwang.param.ArticleCommentParam;

public interface ArticleCommentDao extends GeneralDao<ArticleCommentParam, ArticleCommentModel> {

    int getCount(ArticleCommentParam param);
}
