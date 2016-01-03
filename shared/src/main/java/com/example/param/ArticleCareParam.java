package com.example.param;

import java.util.Date;

public class ArticleCareParam extends PageParam {

    private Long    id;

    private Long    articleId;

    private Long    userId;

    private String  status;

    private Date    gmtCreated;

    private Date    gmtModified;

    private boolean cared;

    public boolean isCared() {
        return cared;
    }

    public void setCared(boolean cared) {
        this.cared = cared;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

}
