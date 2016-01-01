package com.example.controller.article;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.constant.OperationType;
import com.example.constant.SortType;
import com.example.constant.StatusType;
import com.example.controller.AbstractController;
import com.example.param.ArticleParam;
import com.example.service.ArticleService;

@Controller
@RequestMapping("/article")
public class ArticleController extends AbstractController {

    @Resource
    private ArticleService articleService;
    
    @RequestMapping("/detail")
    public ModelAndView detail(ArticleParam param) {
        ModelAndView modelAndView = new ModelAndView();
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
