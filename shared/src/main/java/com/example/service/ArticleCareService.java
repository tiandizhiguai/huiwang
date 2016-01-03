package com.example.service;

import com.example.param.ArticleCareParam;
import com.example.vo.ArticleCare;

public interface ArticleCareService extends GeneralService<ArticleCareParam, ArticleCare> {

    ArticleCare getByArticleId(Long articleId);
}
