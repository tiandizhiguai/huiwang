package com.huiwang.model;

import java.io.Serializable;
import java.util.Date;

public class ArticleCommentModel implements Serializable {

    private static final long serialVersionUID = -1711319805268639569L;

    private Long              id;

    private String            simpleComment;

    private Long              articleId;

    private Long              userId;

    private String            status;

    private Date              gmtCreated;

    private Date              gmtModified;

    private String            comment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}