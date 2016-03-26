package com.huiwang.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.huiwang.dao.ArticleCommentDao;
import com.huiwang.model.ArticleCommentModel;
import com.huiwang.param.ArticleCommentParam;
import com.huiwang.param.ArticleStatisParam;
import com.huiwang.service.ArticleCommentService;
import com.huiwang.service.ArticleStatisService;
import com.huiwang.vo.ArticleComment;
import com.huiwang.vo.ArticleStatis;

@Service
public class AticleCommentServiceImpl implements ArticleCommentService {

    @Resource
    private ArticleCommentDao    commentDao;

    @Resource
    private ArticleStatisService articleStatisService;

    @Override
    public List<ArticleComment> getList(ArticleCommentParam param) {
        List<ArticleComment> vos = new ArrayList<ArticleComment>();
        List<ArticleCommentModel> models = commentDao.getList(param);

        if (!CollectionUtils.isEmpty(models)) {
            for (ArticleCommentModel model : models) {
                vos.add(model2VO(model));
            }
        }

        return vos;
    }

    @Override
    public ArticleComment get(ArticleCommentParam param) {
        List<ArticleComment> vos = getList(param);
        if (!CollectionUtils.isEmpty(vos)) {
            return vos.get(0);
        }
        return null;
    }

    @Override
    public void add(ArticleCommentParam param) {
        String content = param.getComment();
        if (StringUtils.isNotBlank(content) && content.length() > 130) {
            param.setSimpleComment(content.substring(0, 130) + "...");
        } else {
            param.setSimpleComment(content);
        }

        ArticleStatis statis = articleStatisService.getByArticleId(param.getArticleId());
        ArticleStatisParam statisParam = new ArticleStatisParam();

        if (statis != null) {
            statisParam.setId(statis.getId());
            Integer commentSize = statis.getCommentSize();
            if (commentSize != null) {
                statisParam.setCommentSize(commentSize + 1);
            } else {
                statisParam.setCommentSize(1);
            }
            articleStatisService.update(statisParam);
        } else {
            statisParam.setArticleId(param.getArticleId());
            statisParam.setCommentSize(1);
            articleStatisService.add(statisParam);
        }

        commentDao.insert(param);
    }

    @Override
    public void update(ArticleCommentParam param) {
        commentDao.update(param);
    }

    @Override
    public void delete(ArticleCommentParam param) {
        commentDao.delete(param);
    }

    public ArticleComment model2VO(ArticleCommentModel model) {
        ArticleComment vo = new ArticleComment();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd:HH:mm");
        vo.setGmtCreated(format.format(model.getGmtCreated()));
        vo.setGmtModified(format.format(model.getGmtModified()));

        BeanUtils.copyProperties(model, vo);
        return vo;
    }

    public int getCount(ArticleCommentParam param) {
        return commentDao.getCount(param);
    }

    @Override
    public ArticleComment get(Long id) {
        ArticleCommentModel model = commentDao.getById(id);
        if (model != null) {
            return model2VO(model);
        }
        return null;
    }
}
