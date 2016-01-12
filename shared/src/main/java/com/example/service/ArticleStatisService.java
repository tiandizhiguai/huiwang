package com.example.service;

import com.example.param.ArticleCareParam;
import com.example.param.ArticlePraiseParam;
import com.example.param.ArticleStatisParam;
import com.example.vo.ArticleStatis;

public interface ArticleStatisService extends GeneralService<ArticleStatisParam, ArticleStatis> {

    ArticleStatis getByArticleId(Long articleId);

    Integer careArticle(ArticleCareParam param);

    Integer praiseArticle(ArticlePraiseParam param);
}
