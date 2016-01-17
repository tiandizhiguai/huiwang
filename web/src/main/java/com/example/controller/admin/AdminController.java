package com.example.controller.admin;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.constant.Constants;
import com.example.constant.OperationType;
import com.example.constant.StatusType;
import com.example.controller.AbstractController;
import com.example.param.TopicParam;
import com.example.param.UserParam;
import com.example.service.TopicService;
import com.example.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController extends AbstractController {

    @Resource
    private UserService  userServie;

    @Resource
    private TopicService topicService;

    @RequestMapping("/index")
    public ModelAndView index(String operationType) {
        TopicParam topicParam = new TopicParam();
        topicParam.setPageSize(20);
        topicParam.setStatus(StatusType.NORMAL.getValue());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("topicDatas", topicService.getList(topicParam));
        modelAndView.addObject("operationType", operationType);
        modelAndView.setViewName("/admin/index");
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
        modelAndView.addObject("operationType", OperationType.SUCCESS.getValue());
        modelAndView.setViewName("redirect:/admin/index");
        return modelAndView;
    }

    @RequestMapping("/preUploadPhoto")
    public ModelAndView preUploadPhoto(UserParam param) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("data", userServie.get(param));
        modelAndView.setViewName("/admin/preUploadPhoto");
        return modelAndView;
    }

    @RequestMapping("/uploadPhoto")
    public ModelAndView uploadPhoto(UserParam param, @RequestParam MultipartFile[] files) {
        // 创建图片的根目录
        File imgRootDir = new File(Constants.IMG_ABSOLUTE_PATH);
        if (!imgRootDir.exists()) {
            imgRootDir.mkdir();
        }
        // 不能执行该目录
        imgRootDir.setExecutable(false);

        // 创建用户文件目录
        File parent = new File(Constants.ADMIN_IMG_ABSOLUTE_PATH);
        if (!parent.exists()) {
            parent.mkdir();
        }

        List<String> localFileNames = saveFiles(files, parent);
        if (CollectionUtils.isNotEmpty(localFileNames)) {
            param.setPhotoName(localFileNames.get(0));
        }

        userServie.update(param);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("operationType", OperationType.SUCCESS.getValue());
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
