package com.huiwang.controller.article;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.huiwang.constant.SortType;
import com.huiwang.constant.StatusType;
import com.huiwang.controller.AbstractController;
import com.huiwang.param.ArticleCommentParam;
import com.huiwang.param.TopicParam;
import com.huiwang.service.ArticleCommentService;
import com.huiwang.service.biz.ArticleBizService;

@Controller
@RequestMapping("/articleComment")
public class ArticleCommentController extends AbstractController {

    @Resource
    private ArticleBizService     articleBizService;

    @Resource
    private ArticleCommentService articleCommentService;

    @RequestMapping("/getComments")
    public ModelAndView getComments(ArticleCommentParam param) {
        param.setSortType(SortType.ID.getValue());
        param.setDescOrder(false);
        param.setStatus(StatusType.NORMAL.getValue());
        param.setPageSize(10);
        int totalCount = articleCommentService.getCount(param);
        int totalPage = totalCount / param.getPageSize();
        if (totalCount % param.getPageSize() != 0) {
            totalPage++;
        }

        ModelAndView modelAndView = getModelAndView();
        modelAndView.addObject("totalCount", totalCount);
        modelAndView.addObject("pageNo", param.getPageNo());
        modelAndView.addObject("totalPage", totalPage);
        modelAndView.addObject("datas", articleBizService.getCommentList(param));

        modelAndView.setViewName("/article/articleComment");
        return modelAndView;
    }

    @RequestMapping("/detail")
    public ModelAndView detail(Long id) {
        ModelAndView modelAndView = getModelAndView();

        TopicParam topicParam = new TopicParam();
        topicParam.setPageSize(20);
        topicParam.setStatus(StatusType.NORMAL.getValue());

        Long loginedUserId = null;
        if (this.isUserLogined()) {
            loginedUserId = this.getLoginedUser().getId();
        }

        modelAndView.addObject("data", articleBizService.getDetail(loginedUserId, id));
        modelAndView.setViewName("/article/articleDetail");
        return modelAndView;
    }

    @RequestMapping("/addComment")
    public ModelAndView addComment(ArticleCommentParam param) {
        Long articleId = param.getArticleId();
        if (StringUtils.isNotBlank(param.getComment()) && articleId != null && articleId > 0
            && param.getUserId() != null && param.getUserId() > 0) {
            articleCommentService.add(param);
        }
        param = new ArticleCommentParam();
        param.setArticleId(articleId);
        return getLastPageComments(param);
    }

    @RequestMapping("/getLastPageComments")
    public ModelAndView getLastPageComments(ArticleCommentParam param) {
        param.setSortType(SortType.ID.getValue());
        param.setDescOrder(false);
        param.setStatus(StatusType.NORMAL.getValue());
        param.setPageSize(10);
        int totalCount = articleCommentService.getCount(param);
        int totalPage = totalCount / param.getPageSize();
        if (totalCount % param.getPageSize() != 0) {
            totalPage++;
        }
        // 最后一页的数据
        param.setPageNo(totalPage);

        ModelAndView modelAndView = getModelAndView();
        modelAndView.addObject("totalCount", totalCount);
        modelAndView.addObject("pageNo", param.getPageNo());
        modelAndView.addObject("totalPage", totalPage);
        modelAndView.addObject("datas", articleBizService.getCommentList(param));

        modelAndView.setViewName("/article/articleComment");
        return modelAndView;
    }
}
