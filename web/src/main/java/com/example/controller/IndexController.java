package com.example.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.constant.SortType;
import com.example.constant.StatusType;
import com.example.param.ArticleParam;
import com.example.param.TopicParam;
import com.example.service.ArticleService;
import com.example.service.TopicService;
import com.example.service.biz.ArticleBizService;

@Controller
@RequestMapping("/")
public class IndexController extends AbstractController {

    @Resource
    private ArticleBizService articleBizService;

    @Resource
    private ArticleService    articleService;

    @Resource
    private TopicService      topicService;

    @RequestMapping("/index")
    public ModelAndView index(ArticleParam param) {
        if (this.isUserLogined()) {
            param.setLoginUserId(this.getLoginedUser().getId());
        }
        param.setSortType(SortType.ID.getValue());
        param.setDescOrder(true);
        param.setStatus(StatusType.NORMAL.getValue());
        param.setPageSize(20);
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
}
