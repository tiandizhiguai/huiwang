package com.huiwang.dao;

import com.huiwang.model.ArticleModel;
import com.huiwang.param.ArticleParam;

public interface ArticleDao extends GeneralDao<ArticleParam, ArticleModel> {

    int getCount(ArticleParam param);
}
