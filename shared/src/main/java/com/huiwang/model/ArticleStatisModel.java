package com.huiwang.model;

public class ArticleStatisModel extends AbstratModel {

    private static final long serialVersionUID = -9023031274113008341L;

    private Integer           readSize         = 0;

    private Integer           commentSize      = 0;

    private Integer           favoriteSize     = 0;

    private Integer           careSize         = 0;

    private Integer           forwardSize      = 0;

    private Long              articleId;

    private Integer           praiseSize       = 0;

    public Integer getPraiseSize() {
        return praiseSize;
    }

    public void setPraiseSize(Integer praiseSize) {
        this.praiseSize = praiseSize;
    }

    public Integer getReadSize() {
        return readSize;
    }

    public void setReadSize(Integer readSize) {
        this.readSize = readSize;
    }

    public Integer getCommentSize() {
        return commentSize;
    }

    public void setCommentSize(Integer commentSize) {
        this.commentSize = commentSize;
    }

    public Integer getFavoriteSize() {
        return favoriteSize;
    }

    public void setFavoriteSize(Integer favoriteSize) {
        this.favoriteSize = favoriteSize;
    }

    public Integer getCareSize() {
        return careSize;
    }

    public void setCareSize(Integer careSize) {
        this.careSize = careSize;
    }

    public Integer getForwardSize() {
        return forwardSize;
    }

    public void setForwardSize(Integer forwardSize) {
        this.forwardSize = forwardSize;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

}
