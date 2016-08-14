package com.huiwang.model;

public class ArticleCareModel extends AbstratModel {

    private static final long serialVersionUID = 5519857810282530626L;

    private Long              articleId;

    private Long              userId;

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
