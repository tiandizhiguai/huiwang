package com.huiwang.controller.admin;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.huiwang.common.URIUtils;
import com.huiwang.constant.Constants;
import com.huiwang.constant.OperationType;
import com.huiwang.constant.StatusType;
import com.huiwang.controller.AbstractController;
import com.huiwang.param.TopicParam;
import com.huiwang.param.UserParam;
import com.huiwang.service.TopicService;
import com.huiwang.service.UserService;
import com.huiwang.vo.User;

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
        ModelAndView modelAndView = getModelAndView();
        modelAndView.addObject("topicDatas", topicService.getList(topicParam));
        modelAndView.addObject("operationType", operationType);
        modelAndView.setViewName("/admin/index");
        return modelAndView;
    }

    @RequestMapping("/preEditAdmin")
    public ModelAndView preEditAdmin(UserParam param) {
        ModelAndView modelAndView = getModelAndView();
        modelAndView.setViewName("/admin/preEditAdmin");
        return modelAndView;
    }

    @RequestMapping("/editAdmin")
    public ModelAndView editAdmin(UserParam param) {
        userServie.update(param);
        // 修改用户后，要更新登陆信息
        httpSession.setAttribute(Constants.LOGIN_USER, userServie.get(param.getId()));
        ModelAndView modelAndView = getModelAndView();
        modelAndView.addObject("operationType", OperationType.SUCCESS.getValue());
        modelAndView.setViewName("redirect:/admin/index");
        return modelAndView;
    }

    @RequestMapping("/preUploadPhoto")
    public ModelAndView preUploadPhoto(UserParam param) {
        ModelAndView modelAndView = getModelAndView();
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
        String photoName = null;
        if (CollectionUtils.isNotEmpty(localFileNames)) {
            photoName = localFileNames.get(0);
        }

        // 更新用户数据
        param.setPhotoName(photoName);
        userServie.update(param);

        // 更新登陆用户的头像
        User user = this.getLoginedUser();
        user.setFullPhotoUrl(URIUtils.getFullImgUrl(photoName));

        ModelAndView modelAndView = getModelAndView();
        modelAndView.addObject("operationType", OperationType.SUCCESS.getValue());
        modelAndView.setViewName("redirect:/admin/index");
        return modelAndView;
    }

    @RequestMapping("/preEditPassword")
    public ModelAndView preEditPassword(UserParam param) {
        ModelAndView modelAndView = getModelAndView();
        modelAndView.setViewName("/admin/preEditPassword");
        return modelAndView;
    }

    @RequestMapping("/editPassword")
    public ModelAndView editPassword(UserParam param) {
        userServie.update(param);
        // 修改用户后，要更新登陆信息
        httpSession.setAttribute(Constants.LOGIN_USER, userServie.get(param.getId()));
        ModelAndView modelAndView = getModelAndView();
        modelAndView.setViewName("redirect:/admin/index");
        return modelAndView;
    }
}
