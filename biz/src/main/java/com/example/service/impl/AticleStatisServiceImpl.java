package com.example.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.example.dao.ArticleStatisDao;
import com.example.model.ArticleStatisModel;
import com.example.param.ArticleStatisParam;
import com.example.service.ArticleStatisService;
import com.example.vo.ArticleStatisVO;

@Service
public class AticleStatisServiceImpl implements ArticleStatisService {

    @Resource
    private ArticleStatisDao statisDao;

    @Override
    public List<ArticleStatisVO> getList(ArticleStatisParam param) {
        List<ArticleStatisVO> vos = new ArrayList<ArticleStatisVO>();
        List<ArticleStatisModel> models = statisDao.getList(param);

        if (!CollectionUtils.isEmpty(models)) {
            for (ArticleStatisModel model : models) {
                vos.add(model2VO(model));
            }
        }

        return vos;
    }

    @Override
    public ArticleStatisVO get(ArticleStatisParam param) {
        List<ArticleStatisVO> vos = getList(param);
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

    public ArticleStatisVO model2VO(ArticleStatisModel model) {
        ArticleStatisVO vo = new ArticleStatisVO();
        BeanUtils.copyProperties(model, vo);
        return vo;
    }

    @Override
    public ArticleStatisVO get(Long id) {
        ArticleStatisModel model = statisDao.getById(id);
        if (model != null) {
            return model2VO(model);
        }
        return null;
    }
}