package com.huiwang.service.biz;

import java.util.List;

import com.huiwang.param.ArticleCommentParam;
import com.huiwang.param.ArticleParam;
import com.huiwang.service.bo.ArticleBO;
import com.huiwang.service.bo.ArticleCommentBO;

public interface ArticleBizService {

    List<ArticleBO> getList(ArticleParam bizParam);
    
    ArticleBO getDetail(Long loginedUserId, Long articleId);

    List<ArticleCommentBO> getCommentList(ArticleCommentParam param);
}
