package com.huiwang.service;

import com.huiwang.param.ArticleParam;
import com.huiwang.vo.Article;

public interface ArticleService extends GeneralService<ArticleParam, Article> {

    int getCount(ArticleParam param);

}
