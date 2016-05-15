package com.huiwang.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.huiwang.dao.ArticleStatisDao;
import com.huiwang.model.ArticleStatisModel;
import com.huiwang.param.ArticleCareParam;
import com.huiwang.param.ArticlePraiseParam;
import com.huiwang.param.ArticleStatisParam;
import com.huiwang.service.ArticleCareService;
import com.huiwang.service.ArticlePraiseService;
import com.huiwang.service.ArticleStatisService;
import com.huiwang.vo.ArticleStatis;

@Service
public class AticleStatisServiceImpl implements ArticleStatisService {

    @Resource
    private ArticleStatisDao     statisDao;

    @Resource
    private ArticleCareService   articleCareService;

    @Resource
    private ArticlePraiseService articlePraiseService;

    @Override
    public List<ArticleStatis> getList(ArticleStatisParam param) {
        List<ArticleStatis> vos = new ArrayList<ArticleStatis>();
        List<ArticleStatisModel> models = statisDao.getList(param);

        if (!CollectionUtils.isEmpty(models)) {
            for (ArticleStatisModel model : models) {
                vos.add(model2VO(model));
            }
        }

        return vos;
    }

    @Override
    public ArticleStatis get(ArticleStatisParam param) {
        List<ArticleStatis> vos = getList(param);
        if (!CollectionUtils.isEmpty(vos)) {
            return vos.get(0);
        }
        return null;
    }

    @Override
    public ArticleStatis getByArticleId(Long articleId) {
        ArticleStatisParam param = new ArticleStatisParam();
        param.setArticleId(articleId);
        List<ArticleStatis> vos = getList(param);
        if (!CollectionUtils.isEmpty(vos)) {
            return vos.get(0);
        }
        return null;
    }

    @Override
    public void add(ArticleStatisParam param) {
        statisDao.insert(param);
    }

    @Override
    public void update(ArticleStatisParam param) {
        statisDao.update(param);
    }

    @Override
    public void delete(ArticleStatisParam param) {
        statisDao.delete(param);
    }

    public ArticleStatis model2VO(ArticleStatisModel model) {
        ArticleStatis vo = new ArticleStatis();
        BeanUtils.copyProperties(model, vo);
        return vo;
    }

    @Override
    public ArticleStatis get(Long id) {
        ArticleStatisModel model = statisDao.getById(id);
        if (model != null) {
            return model2VO(model);
        }
        return null;
    }

    public Integer praiseArticle(ArticlePraiseParam param) {

        ArticleStatis articleStatis = this.getByArticleId(param.getArticleId());

        // 增加赞
        Integer size = 0;
        ArticleStatisParam statisParam = new ArticleStatisParam();
        if (!param.isPraised()) {
            articlePraiseService.add(param);
            if (articleStatis == null) {
                statisParam.setArticleId(param.getArticleId());
                statisParam.setPraiseSize(1);
                statisDao.insert(statisParam);
                size = 1;
            } else {
                size = articleStatis.getPraiseSize();
                size++;
                statisParam.setPraiseSize(size);
                statisParam.setId(articleStatis.getId());
                statisDao.update(statisParam);
            }
        } else {
            // 取消赞
            if (articleStatis != null) {
                articlePraiseService.delete(param);
                size = articleStatis.getPraiseSize();
                size--;
                statisParam.setPraiseSize(size);
                statisParam.setId(articleStatis.getId());
                statisDao.update(statisParam);
            }
        }
        return size;
    }

    public Integer careArticle(ArticleCareParam param) {

        ArticleStatis articleStatis = this.getByArticleId(param.getArticleId());

        // 增加关注人，其中cared的值为true时，表示当前用户已经已经关注了当前文章
        Integer size = 0;
        ArticleStatisParam statisParam = new ArticleStatisParam();
        if (!param.isCared()) {
            articleCareService.add(param);
            if (articleStatis == null) {
                statisParam.setArticleId(param.getArticleId());
                statisParam.setCareSize(1);
                statisDao.insert(statisParam);
                size = 1;
            } else {
                size = articleStatis.getCareSize();
                size++;
                statisParam.setCareSize(size);
                statisParam.setId(articleStatis.getId());
                statisDao.update(statisParam);
            }
        } else {
            // 取消关注人
            if (articleStatis != null) {
                articleCareService.delete(param);
                size = articleStatis.getCareSize();
                size--;
                statisParam.setCareSize(size);
                statisParam.setId(articleStatis.getId());
                statisDao.update(statisParam);
            }
        }
        return size;
    }
}
