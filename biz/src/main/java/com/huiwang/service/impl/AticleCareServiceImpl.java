package com.huiwang.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.huiwang.dao.ArticleCareDao;
import com.huiwang.model.ArticleCareModel;
import com.huiwang.param.ArticleCareParam;
import com.huiwang.service.ArticleCareService;
import com.huiwang.service.ArticleStatisService;
import com.huiwang.vo.ArticleCare;

@Service
public class AticleCareServiceImpl implements ArticleCareService {

    @Resource
    private ArticleCareDao careDao;

    @Resource
    private ArticleStatisService articleStatisService;

    @Override
    public List<ArticleCare> getList(ArticleCareParam param) {
        List<ArticleCare> vos = new ArrayList<ArticleCare>();
        List<ArticleCareModel> models = careDao.getList(param);

        if (!CollectionUtils.isEmpty(models)) {
            for (ArticleCareModel model : models) {
                vos.add(model2VO(model));
            }
        }

        return vos;
    }

    @Override
    public ArticleCare get(ArticleCareParam param) {
        List<ArticleCare> vos = getList(param);
        if (!CollectionUtils.isEmpty(vos)) {
            return vos.get(0);
        }
        return null;
    }

    @Override
    public ArticleCare getByArticleId(Long articleId) {
        ArticleCareParam param = new ArticleCareParam();
        param.setArticleId(articleId);
        return get(param);
    }

    @Override
    public void add(ArticleCareParam param) {
        careDao.insert(param);
    }

    @Override
    public void update(ArticleCareParam param) {
        ArticleCareModel model = careDao.getById(param.getId());
        if (model != null) {
            careDao.update(param);
        } else {
            careDao.insert(param);
        }
    }

    @Override
    public void delete(ArticleCareParam param) {
        careDao.delete(param);

    }

    private ArticleCare model2VO(ArticleCareModel model) {
        ArticleCare vo = new ArticleCare();
        BeanUtils.copyProperties(model, vo);
        return vo;
    }

    @Override
    public ArticleCare get(Long id) {
        ArticleCareModel model = careDao.getById(id);
        if (model != null) {
            return model2VO(model);
        }
        return null;
    }

}