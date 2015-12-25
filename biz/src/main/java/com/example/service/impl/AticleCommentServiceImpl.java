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
import com.example.vo.ArticleCommentVO;

@Service
public class AticleCommentServiceImpl implements ArticleCommentService {

    @Resource
    private ArticleCommentDao commentDao;

    @Override
    public List<ArticleCommentVO> getList(ArticleCommentParam param) {
        List<ArticleCommentVO> vos = new ArrayList<ArticleCommentVO>();
        List<ArticleCommentModel> models = commentDao.getList(param);

        if (!CollectionUtils.isEmpty(models)) {
            for (ArticleCommentModel model : models) {
                vos.add(model2VO(model));
            }
        }

        return vos;
    }

    @Override
    public ArticleCommentVO get(ArticleCommentParam param) {
        List<ArticleCommentVO> vos = getList(param);
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

    public ArticleCommentVO model2VO(ArticleCommentModel model) {
        ArticleCommentVO vo = new ArticleCommentVO();
        BeanUtils.copyProperties(model, vo);
        return vo;
    }

    @Override
    public ArticleCommentVO get(Long id) {
        ArticleCommentModel model = commentDao.getById(id);
        if (model != null) {
            return model2VO(model);
        }
        return null;
    }
}