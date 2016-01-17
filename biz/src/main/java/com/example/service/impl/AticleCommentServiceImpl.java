package com.example.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.example.dao.ArticleCommentDao;
import com.example.model.ArticleCommentModel;
import com.example.param.ArticleCommentParam;
import com.example.service.ArticleCommentService;
import com.example.vo.ArticleComment;

@Service
public class AticleCommentServiceImpl implements ArticleCommentService {

    @Resource
    private ArticleCommentDao commentDao;

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