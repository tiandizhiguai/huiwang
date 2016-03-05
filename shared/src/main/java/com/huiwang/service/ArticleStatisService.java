package com.huiwang.service;

import com.huiwang.param.ArticleCareParam;
import com.huiwang.param.ArticlePraiseParam;
import com.huiwang.param.ArticleStatisParam;
import com.huiwang.vo.ArticleStatis;

public interface ArticleStatisService extends GeneralService<ArticleStatisParam, ArticleStatis> {

    ArticleStatis getByArticleId(Long articleId);

    Integer careArticle(ArticleCareParam param);

    Integer praiseArticle(ArticlePraiseParam param);
}
