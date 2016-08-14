package com.huiwang.model;


public class ArticleCommentModel extends AbstratModel {

    private static final long serialVersionUID = -7255162498869119558L;

    private String            simpleComment;

    private Long              articleId;

    private Long              userId;

    private String            comment;

    public String getSimpleComment() {
        return simpleComment;
    }

    public void setSimpleComment(String simpleComment) {
        this.simpleComment = simpleComment;
    }

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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
