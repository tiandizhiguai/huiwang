package com.huiwang.controller.article;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.huiwang.constant.OperationType;
import com.huiwang.constant.SortType;
import com.huiwang.constant.StatusType;
import com.huiwang.controller.AbstractController;
import com.huiwang.param.ArticleParam;
import com.huiwang.param.TopicParam;
import com.huiwang.service.ArticleService;
import com.huiwang.service.TopicService;
import com.huiwang.service.biz.ArticleBizService;

@Controller
@RequestMapping("/article")
public class ArticleController extends AbstractController {

    @Resource
    private ArticleService    articleService;

    @Resource
    private ArticleBizService articleBizService;

    @Resource
    private TopicService      topicService;

    @RequestMapping("/detail")
    public ModelAndView detail(Long id) {
        ModelAndView modelAndView = new ModelAndView();

        TopicParam topicParam = new TopicParam();
        topicParam.setPageSize(20);
        topicParam.setStatus(StatusType.NORMAL.getValue());
        modelAndView.addObject("topicDatas", topicService.getList(topicParam));

        Long loginedUserId = null;
        if (this.isUserLogined()) {
            loginedUserId = this.getLoginedUser().getId();
        }

        modelAndView.addObject("data", articleBizService.getDetail(loginedUserId, id));
        modelAndView.setViewName("/article/articleDetail");
        return modelAndView;
    }

    @RequestMapping("/adminArticles")
    public ModelAndView adminArticles(ArticleParam param) {
        param.setSortType(SortType.ID.getValue());
        param.setDescOrder(true);
        param.setStatus(StatusType.NORMAL.getValue());
        param.setPageSize(10);
        int totalCount = articleService.getCount(param);
        int totalPage = totalCount / param.getPageSize();
        if (totalCount % param.getPageSize() != 0) {
            totalPage++;
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("totalCount", totalCount);
        modelAndView.addObject("pageNo", param.getPageNo());
        modelAndView.addObject("totalPage", totalPage);
        modelAndView.addObject("datas", articleService.getList(param));
        modelAndView.setViewName("/admin/adminArticles");

        return modelAndView;
    }

    @RequestMapping("/preAddArticle")
    public ModelAndView preAddArticle(Long id) {
        ModelAndView modelAndView = new ModelAndView();
        if (id != null && id > 0) {
            modelAndView.addObject("data", articleService.get(id));
        }
        modelAndView.setViewName("/admin/preAddArticle");
        return modelAndView;
    }

    @RequestMapping("/preEditArticle")
    public ModelAndView preEditArticle(ArticleParam param) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("data", articleService.get(param));
        modelAndView.setViewName("/admin/preEditArticle");
        return modelAndView;
    }

    @RequestMapping("/addArticle")
    public ModelAndView addArticle(ArticleParam param) {
        if (param.getId() != null && param.getId() > 0) {
            articleService.update(param);
        } else {
            articleService.add(param);
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("operationType", OperationType.SUCCESS.getValue());
        modelAndView.setViewName("redirect:/admin/index");
        return modelAndView;
    }

    @RequestMapping("/delete")
    public ModelAndView delete(ArticleParam param) {
        param.setStatus(StatusType.DEL.getValue());
        articleService.update(param);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("operationType", OperationType.SUCCESS.getValue());
        modelAndView.setViewName("/info/info");
        return modelAndView;
    }

}
