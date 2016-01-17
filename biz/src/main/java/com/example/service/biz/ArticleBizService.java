package com.example.service.biz;

import java.util.List;

import com.example.param.ArticleCommentParam;
import com.example.param.ArticleParam;
import com.example.service.bo.ArticleBO;
import com.example.service.bo.ArticleCommentBO;

public interface ArticleBizService {

    List<ArticleBO> getList(ArticleParam bizParam);
    
    ArticleBO getDetail(Long loginedUserId, Long articleId);

    List<ArticleCommentBO> getCommentList(ArticleCommentParam param);
}
