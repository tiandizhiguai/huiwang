package com.huiwang.service.bo;

import com.huiwang.vo.ArticleComment;
import com.huiwang.vo.User;

public class ArticleCommentBO {

    private ArticleComment comment;
    private User           user;

    public ArticleComment getComment() {
        return comment;
    }

    public void setComment(ArticleComment comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
