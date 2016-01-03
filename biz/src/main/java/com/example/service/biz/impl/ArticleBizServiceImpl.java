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
import com.example.param.ArticleParam;
import com.example.param.ArticlePraiseParam;
import com.example.param.ArticleStatisParam;
import com.example.param.UserParam;
import com.example.service.ArticleCareService;
import com.example.service.ArticlePraiseService;
import com.example.service.ArticleService;
import com.example.service.ArticleStatisService;
import com.example.service.UserService;
import com.example.service.biz.ArticleBizService;
import com.example.service.bo.ArticleBO;
import com.example.vo.Article;
import com.example.vo.ArticleCare;
import com.example.vo.ArticlePraise;
import com.example.vo.ArticleStatis;
import com.example.vo.User;

@Service
public class ArticleBizServiceImpl implements ArticleBizService {

    @Resource
    private ArticleService       articleSerice;

    @Resource
    private ArticleStatisService articleStatisService;

    @Resource
    private UserService          userService;

    @Resource
    private ArticleCareService   articleCareService;

    @Resource
    private ArticlePraiseService articlePraiseService;

    public List<ArticleBO> getList(ArticleParam param) {
        List<ArticleBO> bos = new ArrayList<ArticleBO>();

        // 1.文章信息
        List<Article> articles = articleSerice.getList(param);
        if (CollectionUtils.isEmpty(articles)) {
            return bos;
        }

        List<Long> userIds = new ArrayList<Long>(articles.size());
        List<Long> articleIds = new ArrayList<Long>(articles.size());
        for (Article article : articles) {
            ArticleBO bo = new ArticleBO();
            BeanUtils.copyProperties(article, bo);
            bos.add(bo);
            userIds.add(article.getUserId());
            articleIds.add(article.getId());
        }

        // 2.用户信息
        UserParam userParam = new UserParam();
        userParam.setIds(userIds);
        List<User> users = userService.getList(userParam);
        if (!CollectionUtils.isEmpty(users)) {
            Map<Long, User> userMap = new HashMap<Long, User>(users.size());
            for (User user : users) {
                userMap.put(user.getId(), user);
            }
            for (ArticleBO bo : bos) {
                bo.setUserData(userMap.get(bo.getUserId()));
            }
        }

        // 2.统计数据
        ArticleStatisParam statisParam = new ArticleStatisParam();
        statisParam.setArticleIds(articleIds);
        List<ArticleStatis> statises = articleStatisService.getList(statisParam);
        if (!CollectionUtils.isEmpty(statises)) {
            Map<Long, ArticleStatis> map = new HashMap<Long, ArticleStatis>(statises.size());
            for (ArticleStatis statis : statises) {
                map.put(statis.getArticleId(), statis);
            }
            for (ArticleBO bo : bos) {
                bo.setStatisData(map.get(bo.getId()));
            }
        }

        if (param.isUserLogined()) {

            for (ArticleBO bo : bos) {
                // 关注
                ArticleCareParam careParam = new ArticleCareParam();
                careParam.setArticleId(bo.getId());
                careParam.setUserId(param.getLogoinUserId());
                ArticleCare articleCare = articleCareService.get(careParam);
                bo.setCared(articleCare != null);

                // 赞
                ArticlePraiseParam praiseParam = new ArticlePraiseParam();
                praiseParam.setArticleId(bo.getId());
                praiseParam.setUserId(param.getLogoinUserId());
                ArticlePraise articlePraise = articlePraiseService.get(praiseParam);
                bo.setPraised(articlePraise != null);
            }

        }

        return bos;
    }

}
