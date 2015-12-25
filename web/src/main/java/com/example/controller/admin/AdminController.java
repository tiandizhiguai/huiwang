package com.example.controller.admin;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.constant.Constants;
import com.example.controller.AbstractController;
import com.example.param.UserParam;
import com.example.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController extends AbstractController {

    @Resource
    private UserService       userServie;

    @RequestMapping("/index")
    public ModelAndView index(String operationType) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("operationType", operationType);
        modelAndView.setViewName("/admin/index");
        return modelAndView;
    }

    @RequestMapping("/preUploadPhoto")
    public ModelAndView preUploadPhoto(UserParam param) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/preUploadPhoto");
        return modelAndView;
    }

    @RequestMapping("/preEditAdmin")
    public ModelAndView preEditAdmin(UserParam param) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/preEditAdmin");
        return modelAndView;
    }

    @RequestMapping("/editAdmin")
    public ModelAndView editAdmin(UserParam param) {
        userServie.update(param);
        // 修改用户后，要更新登陆信息
        httpSession.setAttribute(Constants.LOGIN_USER, userServie.get(param.getId()));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/index");
        return modelAndView;
    }

    @RequestMapping("/preEditPassword")
    public ModelAndView preEditPassword(UserParam param) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/preEditPassword");
        return modelAndView;
    }

    @RequestMapping("/editPassword")
    public ModelAndView editPassword(UserParam param) {
        userServie.update(param);
        // 修改用户后，要更新登陆信息
        httpSession.setAttribute(Constants.LOGIN_USER, userServie.get(param.getId()));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/index");
        return modelAndView;
    }
}
