package com.huiwang.controller.message;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.huiwang.constant.SortType;
import com.huiwang.controller.AbstractController;
import com.huiwang.param.CommentMessageParam;
import com.huiwang.service.CommentMessageService;

@Controller
@RequestMapping("/message")
public class MessageController extends AbstractController {

    @Resource
    private CommentMessageService commentMessageService;

    @RequestMapping("/adminMessage")
    public ModelAndView adminMessage(CommentMessageParam param) {
        param.setSortType(SortType.ID.getValue());
        param.setDescOrder(true);
        param.setPageSize(10);
        int totalCount = commentMessageService.getCount(param);
        int totalPage = totalCount / param.getPageSize();
        if (totalCount % param.getPageSize() != 0) {
            totalPage++;
        }
        ModelAndView modelAndView = getModelAndView();
        modelAndView.addObject("totalCount", totalCount);
        modelAndView.addObject("pageNo", param.getPageNo());
        modelAndView.addObject("totalPage", totalPage);
        modelAndView.addObject("datas", commentMessageService.getList(param));
        modelAndView.setViewName("/admin/adminMessage");

        return modelAndView;
    }
}
