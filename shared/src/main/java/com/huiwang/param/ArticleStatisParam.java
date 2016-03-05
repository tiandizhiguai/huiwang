package com.huiwang.param;

import java.util.Date;
import java.util.List;

public class ArticleStatisParam extends PageParam {

    private Long       id;

    private Integer    readSize;

    private Integer    commentSize;

    private Integer    favoriteSize;

    private Integer    careSize;

    private Integer    forwardSize;

    private Long       articleId;

    private List<Long> articleIds;

    private Date       gmtCreated;

    private Date       gmtModified;

    private Integer    praiseSize;

    public List<Long> getArticleIds() {
        return articleIds;
    }

    public void setArticleIds(List<Long> articleIds) {
        this.articleIds = articleIds;
    }

    public Integer getPraiseSize() {
        return praiseSize;
    }

    public void setPraiseSize(Integer praiseSize) {
        this.praiseSize = praiseSize;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}
