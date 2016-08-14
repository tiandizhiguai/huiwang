package com.huiwang.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.huiwang.constant.StatusType;
import com.huiwang.dao.CommentMessageDao;
import com.huiwang.model.CommentMessageModel;
import com.huiwang.param.CommentMessageParam;
import com.huiwang.service.ArticleService;
import com.huiwang.service.CommentMessageService;
import com.huiwang.service.UserService;
import com.huiwang.vo.Article;
import com.huiwang.vo.CommentMessage;
import com.huiwang.vo.User;

@Service
public class CommentMessageServiceImpl implements CommentMessageService {

    @Resource
    private CommentMessageDao commentMessageDao;

    @Resource
    private ArticleService    articleService;

    @Resource
    private UserService       userService;

    @Override
    public List<CommentMessage> getList(CommentMessageParam param) {
        List<CommentMessage> vos = new ArrayList<CommentMessage>();
        List<CommentMessageModel> models = commentMessageDao.getList(param);

        if (!CollectionUtils.isEmpty(models)) {
            for (CommentMessageModel model : models) {
                vos.add(model2VO(model));
            }
        }

        return vos;
    }

    @Override
    public CommentMessage get(CommentMessageParam param) {
        List<CommentMessage> vos = getList(param);
        if (!CollectionUtils.isEmpty(vos)) {
            return vos.get(0);
        }
        return null;
    }

    @Override
    public void add(CommentMessageParam param) {
        Article article = articleService.get(param.getArticleId());
        User user = userService.get(param.getFromUserId());
        if(article != null){
            param.setArticleTitle(article.getTitle());
        }
        if(user != null){
            param.setFromUserName(StringUtils.isNotEmpty(user.getRealName()) ? user.getRealName() : user.getLoginName());
        }

        commentMessageDao.insert(param);
    }

    @Override
    public void update(CommentMessageParam param) {
        commentMessageDao.update(param);
    }

    @Override
    public void delete(CommentMessageParam param) {
        commentMessageDao.delete(param);
    }

    private CommentMessage model2VO(CommentMessageModel model) {
        CommentMessage vo = new CommentMessage();
        BeanUtils.copyProperties(model, vo);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd:HH:mm");
        vo.setGmtCreated(format.format(model.getGmtCreated()));
        
        if (StatusType.UNREAD.getValue().equals(model.getStatus())) {
            vo.setStatusName("未读");
        } else if (StatusType.READ.getValue().equals(model.getStatus())) {
            vo.setStatusName("已读");
        }

        return vo;
    }

    @Override
    public CommentMessage get(Long id) {
        CommentMessageModel model = commentMessageDao.getById(id);
        if (model != null) {
            return model2VO(model);
        }
        return null;
    }

    @Override
    public int getCount(CommentMessageParam param) {
        return commentMessageDao.getCount(param);
    }

}
