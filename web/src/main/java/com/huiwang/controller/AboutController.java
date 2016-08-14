package com.huiwang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class AboutController extends AbstractController {

    @RequestMapping("/about")
    public ModelAndView about() {
        ModelAndView modelAndView = getModelAndView();
        modelAndView.setViewName("/about/about");
        return modelAndView;
    }

    @RequestMapping("/contact")
    public ModelAndView contact() {
        ModelAndView modelAndView = getModelAndView();
        modelAndView.setViewName("/about/contact");
        return modelAndView;
    }
}
