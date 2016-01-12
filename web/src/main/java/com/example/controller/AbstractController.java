package com.example.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.example.constant.Constants;
import com.example.vo.User;

public class AbstractController {

    protected Logger             logger = LogManager.getLogger(AbstractController.class);

    @Resource
    protected HttpSession        httpSession;

    @Resource
    protected HttpServletRequest httpServletRequest;

    protected User getLoginedUser() {
        return (User) httpSession.getAttribute(Constants.LOGIN_USER);
    }

    protected boolean isUserLogined() {
        return getLoginedUser() != null;
    }

    protected List<String> saveFiles(MultipartFile[] files, File parentPath) {
        if (files == null) {
            return null;
        }
        List<String> localFileNames = new ArrayList<String>(files.length);
        for (MultipartFile file : files) {
            // 创建文件
            String ofileName = file.getOriginalFilename();
            if (StringUtils.isNotBlank(ofileName)) {
                String extendName = ofileName.substring(ofileName.lastIndexOf("."), ofileName.length());
                String fileName = Math.abs(ofileName.hashCode()) + "_" + new Date().getTime() + extendName;
                File localFile = new File(parentPath, fileName);
                try {
                    file.transferTo(localFile);
                    localFileNames.add(fileName);
                } catch (Exception e) {
                    logger.error("Failed to upload file, name is : " + ofileName, e);
                }
            }
        }
        return localFileNames;
    }

}
