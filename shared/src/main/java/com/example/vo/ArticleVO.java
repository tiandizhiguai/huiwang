package com.example.vo;

public class ArticleVO {

    private Long            id;

    private String          title;

    private String          simpleContent;

    private String          content;

    private Long            topicId;

    private Long            userId;

    private String          status;

    private String          gmtCreated;

    private String          gmtModified;

    private ArticleStatisVO statisData;

    private UserVO          userData;

    private String          topicName;

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public UserVO getUserData() {
        return userData;
    }

    public void setUserData(UserVO userData) {
        this.userData = userData;
    }

    public ArticleStatisVO getStatisData() {
        return statisData;
    }

    public void setStatisData(ArticleStatisVO statisData) {
        this.statisData = statisData;
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

    public String getTitle(int length) {
        if (title == null || title.length() <= length) {
            return title;
        }
        return title.substring(0, length) + "...";
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
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
}
