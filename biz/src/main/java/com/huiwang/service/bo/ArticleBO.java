package com.huiwang.service.bo;

import com.huiwang.vo.ArticleStatis;
import com.huiwang.vo.User;

public class ArticleBO {

    private Long          id;

    private String        title;

    private String        simpleContent;

    private String        content;

    private Long          topicId;

    private Long          userId;

    private String        status;

    private String        gmtCreated;

    private String        gmtModified;

    private ArticleStatis statisData;

    private String        topicName;

    private User          userData;

    private Boolean       cared;

    private Boolean       praised;

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSimpleContent() {
        return simpleContent;
    }

    public void setSimpleContent(String simpleContent) {
        this.simpleContent = simpleContent;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
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

    public String getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(String gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public String getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(String gmtModified) {
        this.gmtModified = gmtModified;
    }

    public ArticleStatis getStatisData() {
        return statisData;
    }

    public void setStatisData(ArticleStatis statisData) {
        this.statisData = statisData;
    }

    public User getUserData() {
        return userData;
    }

    public void setUserData(User userData) {
        this.userData = userData;
    }

    public Boolean getCared() {
        return cared;
    }

    public void setCared(Boolean cared) {
        this.cared = cared;
    }

    public Boolean getPraised() {
        return praised;
    }

    public void setPraised(Boolean praised) {
        this.praised = praised;
    }

}
