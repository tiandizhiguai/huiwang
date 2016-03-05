package com.huiwang.service;

import com.huiwang.param.ArticleCareParam;
import com.huiwang.vo.ArticleCare;

public interface ArticleCareService extends GeneralService<ArticleCareParam, ArticleCare> {

    ArticleCare getByArticleId(Long articleId);
}
