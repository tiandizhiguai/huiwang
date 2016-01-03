package com.example.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

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
        commentDao.insert(param);
    }

    @Override
    public void update(ArticleCommentParam param) {
        ArticleCommentModel model = commentDao.getById(param.getId());
        if (model != null) {
            commentDao.update(param);
        } else {
            commentDao.insert(param);
        }
    }

    @Override
    public void delete(ArticleCommentParam param) {
        commentDao.delete(param);

    }

    public ArticleComment model2VO(ArticleCommentModel model) {
        ArticleComment vo = new ArticleComment();
        BeanUtils.copyProperties(model, vo);
        return vo;
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