package com.example.service;

import com.example.param.ArticleParam;
import com.example.vo.Article;

public interface ArticleService extends GeneralService<ArticleParam, Article> {

    int getCount(ArticleParam param);

}
