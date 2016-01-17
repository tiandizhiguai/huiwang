package com.example.service.biz.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.example.param.ArticleCareParam;
import com.example.param.ArticleCommentParam;
import com.example.param.ArticleParam;
import com.example.param.ArticlePraiseParam;
import com.example.param.ArticleStatisParam;
import com.example.param.TopicParam;
import com.example.param.UserParam;
import com.example.service.ArticleCareService;
import com.example.service.ArticleCommentService;
import com.example.service.ArticlePraiseService;
import com.example.service.ArticleService;
import com.example.service.ArticleStatisService;
import com.example.service.TopicService;
import com.example.service.UserService;
import com.example.service.biz.ArticleBizService;
import com.example.service.bo.ArticleBO;
import com.example.service.bo.ArticleCommentBO;
import com.example.vo.Article;
import com.example.vo.ArticleCare;
import com.example.vo.ArticleComment;
import com.example.vo.ArticlePraise;
import com.example.vo.ArticleStatis;
import com.example.vo.Topic;
import com.example.vo.User;

@Service
public class ArticleBizServiceImpl implements ArticleBizService {

    @Resource
    private ArticleService        articleService;

    @Resource
    private ArticleStatisService  articleStatisService;

    @Resource
    private UserService           userService;

    @Resource
    private ArticleCareService    articleCareService;

    @Resource
    private ArticlePraiseService  articlePraiseService;

    @Resource
    private TopicService          topicService;

    @Resource
    private ArticleCommentService articleCommentService;

    public List<ArticleBO> getList(ArticleParam param) {
        List<ArticleBO> bos = new ArrayList<ArticleBO>();

        // 1.文章信息
        List<Article> articles = articleService.getList(param);
        if (CollectionUtils.isEmpty(articles)) {
            return bos;
        }

        List<Long> userIds = new ArrayList<Long>(articles.size());
        List<Long> topicIds = new ArrayList<Long>(articles.size());
        List<Long> articleIds = new ArrayList<Long>(articles.size());
        for (Article article : articles) {
            ArticleBO bo = new ArticleBO();
            BeanUtils.copyProperties(article, bo);
            bos.add(bo);
            userIds.add(article.getUserId());
            articleIds.add(article.getId());
            topicIds.add(article.getTopicId());
        }

        // 2.用户信息
        UserParam userParam = new UserParam();
        userParam.setIds(userIds);
        List<User> users = userService.getList(userParam);
        Map<Long, User> userMap = new HashMap<Long, User>(32);
        if (!CollectionUtils.isEmpty(users)) {
            for (User user : users) {
                userMap.put(user.getId(), user);
            }
        }

        // 3.统计数据
        ArticleStatisParam statisParam = new ArticleStatisParam();
        statisParam.setArticleIds(articleIds);
        List<ArticleStatis> statises = articleStatisService.getList(statisParam);
        Map<Long, ArticleStatis> statisMap = new HashMap<Long, ArticleStatis>(32);
        if (!CollectionUtils.isEmpty(statises)) {
            for (ArticleStatis statis : statises) {
                statisMap.put(statis.getArticleId(), statis);
            }
        }

        // 4.话题
        TopicParam topicParam = new TopicParam();
        topicParam.setIds(topicIds);
        List<Topic> topics = topicService.getList(topicParam);
        Map<Long, String> topicMap = new HashMap<Long, String>(16);
        if (!CollectionUtils.isEmpty(topics)) {
            for (Topic topic : topics) {
                topicMap.put(topic.getId(), topic.getName());
            }
        }

        // 5.只有登录用户才能取关注和赞数据
        Map<Long, ArticleCare> careMap = new HashMap<Long, ArticleCare>(32);
        Map<Long, ArticlePraise> praiseMap = new HashMap<Long, ArticlePraise>(32);
        if (param.getLoginUserId() != null) {
            // 关注
            ArticleCareParam careParam = new ArticleCareParam();
            careParam.setArticleIds(articleIds);
            careParam.setUserId(param.getLoginUserId());
            List<ArticleCare> articleCares = articleCareService.getList(careParam);
            if (!CollectionUtils.isEmpty(articleCares)) {
                for (ArticleCare articleCare : articleCares) {
                    careMap.put(articleCare.getArticleId(), articleCare);
                }
            }

            // 赞
            ArticlePraiseParam praiseParam = new ArticlePraiseParam();
            praiseParam.setArticleIds(articleIds);
            praiseParam.setUserId(param.getLoginUserId());
            List<ArticlePraise> articlePraises = articlePraiseService.getList(praiseParam);
            if (!CollectionUtils.isEmpty(articlePraises)) {
                for (ArticlePraise articlePraise : articlePraises) {
                    praiseMap.put(articlePraise.getArticleId(), articlePraise);
                }
            }
        }

        for (ArticleBO bo : bos) {
            bo.setUserData(userMap.get(bo.getUserId()));
            bo.setStatisData(statisMap.get(bo.getId()));
            bo.setCared(careMap.get(bo.getId()) != null);
            bo.setPraised(praiseMap.get(bo.getId()) != null);
            bo.setTopicName(topicMap.get(bo.getTopicId()));
        }

        return bos;
    }

    public ArticleBO getDetail(Long loginedUserId, Long articleId) {
        ArticleBO bo = new ArticleBO();

        // 1.文章信息
        Article article = articleService.get(articleId);
        if (article == null) {
            return bo;
        }

        BeanUtils.copyProperties(article, bo);

        // 2.用户信息
        User user = userService.get(article.getUserId());

        // 3.统计数据
        ArticleStatis statis = articleStatisService.getByArticleId(article.getId());

        // 4.话题
        Topic topic = topicService.get(article.getTopicId());

        // 5.只有登录用户才能取关注和赞数据
        ArticleCare articleCare = null;
        ArticlePraise articlePraise = null;
        if (loginedUserId != null) {
            // 关注
            ArticleCareParam careParam = new ArticleCareParam();
            careParam.setArticleId(article.getId());
            careParam.setUserId(loginedUserId);
            articleCare = articleCareService.get(careParam);

            // 赞
            ArticlePraiseParam praiseParam = new ArticlePraiseParam();
            praiseParam.setArticleId(article.getId());
            praiseParam.setUserId(loginedUserId);
            articlePraise = articlePraiseService.get(praiseParam);
        }

        // 6.记录阅读次数
        ArticleStatis articleStatis = articleStatisService.getByArticleId(articleId);
        if (articleStatis != null) {
            Integer readSize = articleStatis.getReadSize();
            if (readSize != null) {
                readSize++;
            } else {
                readSize = 1;
            }
            ArticleStatisParam statisParam = new ArticleStatisParam();
            statisParam.setId(articleStatis.getId());
            statisParam.setReadSize(readSize);
            articleStatisService.update(statisParam);
        }

        bo.setUserData(user);
        bo.setStatisData(statis);
        bo.setCared(articleCare != null);
        bo.setPraised(articlePraise != null);
        bo.setTopicName(topic.getName());

        return bo;
    }

    public List<ArticleCommentBO> getCommentList(ArticleCommentParam param) {
        List<ArticleCommentBO> bos = new ArrayList<ArticleCommentBO>();

        // 1.评论信息
        List<ArticleComment> articleComments = articleCommentService.getList(param);
        if (CollectionUtils.isEmpty(articleComments)) {
            return bos;
        }

        List<Long> userIds = new ArrayList<Long>(articleComments.size());
        for (ArticleComment articleComment : articleComments) {
            ArticleCommentBO bo = new ArticleCommentBO();
            bo.setComment(articleComment);
            bos.add(bo);
            userIds.add(articleComment.getUserId());
        }

        // 2.用户信息
        UserParam userParam = new UserParam();
        userParam.setIds(userIds);
        List<User> users = userService.getList(userParam);
        Map<Long, User> userMap = new HashMap<Long, User>(16);
        if (!CollectionUtils.isEmpty(users)) {
            for (User user : users) {
                userMap.put(user.getId(), user);
            }
        }

        for (ArticleCommentBO bo : bos) {
            bo.setUser(userMap.get(bo.getComment().getUserId()));
        }

        return bos;
    }
}
