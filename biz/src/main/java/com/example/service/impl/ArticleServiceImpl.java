package com.example.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.example.dao.ArticleDao;
import com.example.model.ArticleModel;
import com.example.param.ArticleParam;
import com.example.param.ArticleStatisParam;
import com.example.service.ArticleService;
import com.example.service.ArticleStatisService;
import com.example.vo.ArticleStatisVO;
import com.example.vo.ArticleVO;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleDao           articleDao;

    @Resource
    private ArticleStatisService articleStatisService;

    @Override
    public List<ArticleVO> getList(ArticleParam param) {
        List<ArticleVO> vos = new ArrayList<ArticleVO>();
        List<ArticleModel> models = articleDao.getList(param);

        if (!CollectionUtils.isEmpty(models)) {
            for (ArticleModel model : models) {
                vos.add(model2VO(model));
            }
        }

        return vos;
    }

    @Override
    public ArticleVO get(ArticleParam param) {
        List<ArticleVO> vos = getList(param);
        if (!CollectionUtils.isEmpty(vos)) {
            return vos.get(0);
        }
        return null;
    }

    @Override
    public void add(ArticleParam param) {
        articleDao.insert(param);
    }

    @Override
    public void update(ArticleParam param) {
        articleDao.update(param);
    }

    @Override
    public void delete(ArticleParam param) {
        articleDao.delete(param);
    }

    public ArticleVO model2VO(ArticleModel model) {
        ArticleVO vo = new ArticleVO();
        BeanUtils.copyProperties(model, vo);

        ArticleStatisParam param = new ArticleStatisParam();
        param.setArticleId(model.getId());
        List<ArticleStatisVO> statisDatas = articleStatisService.getList(param);
        if (!CollectionUtils.isEmpty(statisDatas)) {
            vo.setStatisData(statisDatas.get(0));
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd:HH:mm");
        vo.setGmtCreated(format.format(model.getGmtCreated()));
        vo.setGmtModified(format.format(model.getGmtModified()));

        return vo;
    }

    @Override
    public ArticleVO get(Long id) {
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
