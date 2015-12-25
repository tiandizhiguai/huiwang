package com.example.dao;

import com.example.model.ArticleModel;
import com.example.param.ArticleParam;

public interface ArticleDao extends GeneralDao<ArticleParam, ArticleModel> {

    int getCount(ArticleParam param);
}
