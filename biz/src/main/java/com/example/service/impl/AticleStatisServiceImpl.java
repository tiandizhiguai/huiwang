package com.example.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.example.dao.ArticleStatisDao;
import com.example.model.ArticleStatisModel;
import com.example.param.ArticleCareParam;
import com.example.param.ArticlePraiseParam;
import com.example.param.ArticleStatisParam;
import com.example.service.ArticleCareService;
import com.example.service.ArticlePraiseService;
import com.example.service.ArticleStatisService;
import com.example.vo.ArticleStatis;

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
        ArticleStatisModel model = statisDao.getById(param.getId());
        if (model != null) {
            statisDao.update(param);
        } else {
            statisDao.insert(param);
        }
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

    public int careArticle(ArticleCareParam param) {

        ArticleStatis articleStatis = this.getByArticleId(param.getArticleId());
        ArticleStatisParam statisParam = new ArticleStatisParam();

        if (articleStatis == null) {
            statisParam.setArticleId(param.getArticleId());
            statisParam.setCareSize(1);
            statisDao.insert(statisParam);
            return 1;
        }

        int size = articleStatis.getCareSize();
        statisParam.setId(articleStatis.getId());

        // 增加关注人
        if (param.isCared()) {
            articleCareService.add(param);
            size++;
            statisParam.setCareSize(size);
        } else {
            // 取消关注人
            articleCareService.delete(param);
            size--;
            statisParam.setCareSize(size);
        }

        statisDao.update(statisParam);

        return size;
    }

    public int praiseArticle(ArticlePraiseParam param) {

        ArticleStatis articleStatis = this.getByArticleId(param.getArticleId());
        ArticleStatisParam statisParam = new ArticleStatisParam();

        if (articleStatis == null) {
            statisParam.setArticleId(param.getArticleId());
            statisParam.setPraiseSize(1);
            statisDao.insert(statisParam);
            return 1;
        }

        int praiseSize = articleStatis.getPraiseSize();
        statisParam.setId(articleStatis.getId());

        // 增加赞
        if (param.isPraised()) {
            articlePraiseService.add(param);
            praiseSize++;
            statisParam.setPraiseSize(praiseSize);
        } else {
            // 取消赞
            articlePraiseService.delete(param);
            praiseSize--;
            statisParam.setPraiseSize(praiseSize);
        }

        statisDao.update(statisParam);

        return praiseSize;
    }
}
