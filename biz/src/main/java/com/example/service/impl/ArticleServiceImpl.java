package com.example.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.example.dao.ArticleDao;
import com.example.model.ArticleModel;
import com.example.param.ArticleParam;
import com.example.param.ArticleStatisParam;
import com.example.param.UserParam;
import com.example.service.ArticleService;
import com.example.service.ArticleStatisService;
import com.example.service.TopicService;
import com.example.service.UserService;
import com.example.vo.ArticleStatisVO;
import com.example.vo.ArticleVO;
import com.example.vo.UserVO;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleDao           articleDao;

    @Resource
    private ArticleStatisService articleStatisService;

    @Resource
    private UserService          userService;

    @Resource
    private TopicService         topicService;

    @Override
    public List<ArticleVO> getList(ArticleParam param) {
        List<ArticleVO> vos = new ArrayList<ArticleVO>();

        // 1.文章信息
        List<ArticleModel> models = articleDao.getList(param);
        if (CollectionUtils.isEmpty(models)) {
            return vos;
        }

        List<Long> userIds = new ArrayList<Long>(models.size());
        for (ArticleModel model : models) {
            vos.add(model2VO(model));
            userIds.add(model.getUserId());
        }

        // 2.用户信息
        UserParam userParam = new UserParam();
        userParam.setIds(userIds);
        List<UserVO> userVOs = userService.getList(userParam);
        
        if (CollectionUtils.isEmpty(userVOs)) {
            return vos;
        }

        Map<Long, UserVO> userMap = new HashMap<Long, UserVO>(userVOs.size());
        for (UserVO userVo : userVOs) {
            userMap.put(userVo.getId(), userVo);
        }

        // 组合用户和文章信息
        for (ArticleVO articleVO : vos) {
            articleVO.setUserData(userMap.get(articleVO.getUserId()));
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
        String content = param.getContent();
        if (StringUtils.isNotBlank(content) && content.length() > 200) {
            param.setSimpleContent(content.substring(0, 200) + "...");
        } else {
            param.setSimpleContent(content);
        }

        param.setTopicName(topicService.getTopicName(param.getTopicId()));

        articleDao.insert(param);
    }

    @Override
    public void update(ArticleParam param) {
        param.setTopicName(topicService.getTopicName(param.getTopicId()));
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
