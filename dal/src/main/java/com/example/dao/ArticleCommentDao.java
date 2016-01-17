package com.example.dao;

import com.example.model.ArticleCommentModel;
import com.example.param.ArticleCommentParam;

public interface ArticleCommentDao extends GeneralDao<ArticleCommentParam, ArticleCommentModel> {

    int getCount(ArticleCommentParam param);
}
