package com.huiwang.model;

public class UserIdeaModel extends AbstratModel {

    private static final long serialVersionUID = -6339223472528452538L;

    private String            content;

    private Long              userId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
