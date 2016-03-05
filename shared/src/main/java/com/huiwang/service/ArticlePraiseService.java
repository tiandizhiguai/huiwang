package com.huiwang.service;

import com.huiwang.param.ArticlePraiseParam;
import com.huiwang.vo.ArticlePraise;

public interface ArticlePraiseService extends GeneralService<ArticlePraiseParam, ArticlePraise> {

    ArticlePraise getByArticleId(Long articleId);
}
