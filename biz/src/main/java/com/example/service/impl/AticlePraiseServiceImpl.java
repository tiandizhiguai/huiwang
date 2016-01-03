package com.example.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.example.dao.ArticlePraiseDao;
import com.example.model.ArticlePraiseModel;
import com.example.param.ArticlePraiseParam;
import com.example.service.ArticlePraiseService;
import com.example.vo.ArticlePraise;

@Service
public class AticlePraiseServiceImpl implements ArticlePraiseService {

    @Resource
    private ArticlePraiseDao praiseDao;

    @Override
    public List<ArticlePraise> getList(ArticlePraiseParam param) {
        List<ArticlePraise> vos = new ArrayList<ArticlePraise>();
        List<ArticlePraiseModel> models = praiseDao.getList(param);

        if (!CollectionUtils.isEmpty(models)) {
            for (ArticlePraiseModel model : models) {
                vos.add(model2VO(model));
            }
        }

        return vos;
    }

    @Override
    public ArticlePraise get(ArticlePraiseParam param) {
        List<ArticlePraise> vos = getList(param);
        if (!CollectionUtils.isEmpty(vos)) {
            return vos.get(0);
        }
        return null;
    }

    @Override
    public ArticlePraise getByArticleId(Long articleId) {
        ArticlePraiseParam param = new ArticlePraiseParam();
        param.setArticleId(articleId);
        return get(param);
    }

    @Override
    public void add(ArticlePraiseParam param) {
        praiseDao.insert(param);
    }

    @Override
    public void update(ArticlePraiseParam param) {
        ArticlePraiseModel model = praiseDao.getById(param.getId());
        if (model != null) {
            praiseDao.update(param);
        } else {
            praiseDao.insert(param);
        }
    }

    @Override
    public void delete(ArticlePraiseParam param) {
        praiseDao.delete(param);

    }

    private ArticlePraise model2VO(ArticlePraiseModel model) {
        ArticlePraise vo = new ArticlePraise();
        BeanUtils.copyProperties(model, vo);
        return vo;
    }

    @Override
    public ArticlePraise get(Long id) {
        ArticlePraiseModel model = praiseDao.getById(id);
        if (model != null) {
            return model2VO(model);
        }
        return null;
    }

}