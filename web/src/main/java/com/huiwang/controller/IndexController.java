package com.huiwang.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.huiwang.constant.SortType;
import com.huiwang.constant.StatusType;
import com.huiwang.param.ArticleParam;
import com.huiwang.param.TopicParam;
import com.huiwang.service.ArticleService;
import com.huiwang.service.TopicService;
import com.huiwang.service.biz.ArticleBizService;
import com.huiwang.service.biz.JokeBizService;

@Controller
@RequestMapping("/")
public class IndexController extends AbstractController {

    @Resource
    private ArticleBizService articleBizService;

    @Resource
    private ArticleService    articleService;

    @Resource
    private TopicService      topicService;

    @Resource
    private JokeBizService    jokeBizService;

    @RequestMapping("/index")
    public ModelAndView index(ArticleParam param) {
        if (param.getTopicId() != null && param.getTopicId() == 11L) {
            return joke(param);
        } else {
            return article(param);
        }
    }

    public ModelAndView article(ArticleParam param) {

        if (this.isUserLogined()) {
            param.setLoginUserId(this.getLoginedUser().getId());
        }
        param.setSortType(SortType.GMT_MODIFIED.getValue());
        param.setDescOrder(true);
        param.setStatus(StatusType.NORMAL.getValue());
        param.setPageSize(15);
        int totalCount = articleService.getCount(param);
        int totalPage = totalCount / param.getPageSize();
        if (totalCount % param.getPageSize() != 0) {
            totalPage++;
        }
        TopicParam topicParam = new TopicParam();
        topicParam.setPageSize(20);
        topicParam.setStatus(StatusType.NORMAL.getValue());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("totalCount", totalCount);
        modelAndView.addObject("pageNo", param.getPageNo());
        modelAndView.addObject("totalPage", totalPage);
        modelAndView.addObject("datas", articleBizService.getList(param));
        modelAndView.addObject("topicDatas", topicService.getList(topicParam));
        modelAndView.addObject("selectedTopicId", param.getTopicId());

        modelAndView.setViewName("/index/index");
        return modelAndView;
    }

    public ModelAndView joke(ArticleParam param) {
        param.setPageSize(15);
        int totalCount = 30000;
        int totalPage = totalCount / param.getPageSize();
        if (totalCount % param.getPageSize() != 0) {
            totalPage++;
        }
        TopicParam topicParam = new TopicParam();
        topicParam.setPageSize(20);
        topicParam.setStatus(StatusType.NORMAL.getValue());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("totalCount", totalCount);
        modelAndView.addObject("pageNo", param.getPageNo());
        modelAndView.addObject("totalPage", totalPage);
        modelAndView.addObject("datas", jokeBizService.getList(param));
        modelAndView.addObject("topicDatas", topicService.getList(topicParam));
        modelAndView.addObject("selectedTopicId", param.getTopicId());

        modelAndView.setViewName("/index/index");
        return modelAndView;
    }
}
