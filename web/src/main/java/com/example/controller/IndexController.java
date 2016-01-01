package com.example.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.constant.SortType;
import com.example.constant.StatusType;
import com.example.param.ArticleParam;
import com.example.service.ArticleService;

@Controller
@RequestMapping("/")
public class IndexController extends AbstractController {

    @Resource
    private ArticleService articleService;
    
    @RequestMapping("/index")
    public ModelAndView index(ArticleParam param) {
        param.setSortType(SortType.ID.getValue());
        param.setDescOrder(true);
        param.setStatus(StatusType.NORMAL.getValue());
        param.setPageSize(20);
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
        modelAndView.setViewName("/index/index");
        return modelAndView;
    }
}
