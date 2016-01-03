package com.example.service;

import com.example.param.ArticlePraiseParam;
import com.example.vo.ArticlePraise;

public interface ArticlePraiseService extends GeneralService<ArticlePraiseParam, ArticlePraise> {

    ArticlePraise getByArticleId(Long articleId);
}
