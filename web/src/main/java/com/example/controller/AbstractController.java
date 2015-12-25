package com.example.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AbstractController {

    protected Logger             logger = LogManager.getLogger(AbstractController.class);

    @Resource
    protected HttpSession        httpSession;

    @Resource
    protected HttpServletRequest httpServletRequest;

}
