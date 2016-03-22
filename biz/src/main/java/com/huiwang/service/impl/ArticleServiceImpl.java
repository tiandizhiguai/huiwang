package com.huiwang.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.huiwang.constant.StatusType;
import com.huiwang.dao.ArticleDao;
import com.huiwang.model.ArticleModel;
import com.huiwang.param.ArticleCommentParam;
import com.huiwang.param.ArticleParam;
import com.huiwang.param.ArticleStatisParam;
import com.huiwang.service.ArticleCommentService;
import com.huiwang.service.ArticleService;
import com.huiwang.service.ArticleStatisService;
import com.huiwang.service.TopicService;
import com.huiwang.service.UserService;
import com.huiwang.vo.Article;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleDao           articleDao;

    @Resource
    private ArticleStatisService articleStatisService;

    @Resource
    private UserService          userService;

    @Resource
    private TopicService         topicService;

    @Resource
    private ArticleCommentService articleCommentService;

    @Override
    public List<Article> getList(ArticleParam param) {
        List<Article> vos = new ArrayList<Article>();

        // 1.文章信息
        List<ArticleModel> models = articleDao.getList(param);
        if (CollectionUtils.isEmpty(models)) {
            return vos;
        }

        for (ArticleModel model : models) {
            vos.add(model2VO(model));
        }
        return vos;
    }

    @Override
    public Article get(ArticleParam param) {
        List<Article> vos = getList(param);
        if (!CollectionUtils.isEmpty(vos)) {
            return vos.get(0);
        }
        return null;
    }

    @Override
    public void add(ArticleParam param) {
        String content = param.getContent();
        if (StringUtils.isNotBlank(content) && content.length() > 200) {
            param.setSimpleContent(content.substring(0, 200) + "...");
        } else {
            param.setSimpleContent(content);
        }

        param.setTopicName(topicService.getTopicName(param.getTopicId()));

        articleDao.insert(param);
    }

    @Override
    public void update(ArticleParam param) {
        param.setTopicName(topicService.getTopicName(param.getTopicId()));
        articleDao.update(param);
    }

    @Override
    public void delete(ArticleParam param) {

        param.setStatus(StatusType.DEL.getValue());
        articleDao.update(param);

        ArticleStatisParam statisParam = new ArticleStatisParam();
        statisParam.setArticleId(param.getId());
        articleStatisService.delete(statisParam);

        ArticleCommentParam articleCommentParam = new ArticleCommentParam();
        articleCommentParam.setArticleId(param.getId());
        articleCommentService.delete(articleCommentParam);
    }

    public Article model2VO(ArticleModel model) {
        Article vo = new Article();
        BeanUtils.copyProperties(model, vo);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd:HH:mm");
        vo.setGmtCreated(format.format(model.getGmtCreated()));
        vo.setGmtModified(format.format(model.getGmtModified()));

        return vo;
    }

    @Override
    public Article get(Long id) {
        ArticleModel model = articleDao.getById(id);
        if (model != null) {
            return model2VO(model);
        }
        return null;
    }

    @Override
    public int getCount(ArticleParam param) {
        return articleDao.getCount(param);
    }

}
