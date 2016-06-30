package com.huiwang.service.biz;

import java.util.List;

import com.huiwang.param.ArticleParam;

public interface JokeBizService {

    List<String> getList(ArticleParam bizParam);
}
