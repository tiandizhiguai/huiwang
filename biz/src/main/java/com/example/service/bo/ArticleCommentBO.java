package com.example.service.bo;

import com.example.vo.ArticleComment;
import com.example.vo.User;

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
