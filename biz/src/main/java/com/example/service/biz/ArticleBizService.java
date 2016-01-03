package com.example.service.biz;

import java.util.List;

import com.example.param.ArticleParam;
import com.example.service.bo.ArticleBO;

public interface ArticleBizService {

    List<ArticleBO> getList(ArticleParam bizParam);
}
