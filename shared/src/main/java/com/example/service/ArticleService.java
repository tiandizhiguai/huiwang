package com.example.service;

import com.example.param.ArticleParam;
import com.example.vo.ArticleVO;

public interface ArticleService extends GeneralService<ArticleParam, ArticleVO> {

    int getCount(ArticleParam param);

}
