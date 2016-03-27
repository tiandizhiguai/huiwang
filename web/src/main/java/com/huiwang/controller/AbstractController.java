package com.huiwang.controller;

import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.huiwang.common.PathUtils;
import com.huiwang.constant.Constants;
import com.huiwang.result.RestfulResult;
import com.huiwang.vo.User;

public class AbstractController {

    protected Logger             logger = LogManager.getLogger(AbstractController.class);

    @Resource
    protected HttpSession        httpSession;

    @Resource
    protected HttpServletRequest httpServletRequest;

    @Resource
    protected HttpServletResponse httpServletResponse;

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

    protected JSONPObject getJsonpData(Map<String, Object> datas, String templateName) {

        Properties p = new Properties();
        p.setProperty(VelocityEngine.INPUT_ENCODING, "UTF-8");
        p.setProperty(VelocityEngine.OUTPUT_ENCODING, "UTF-8");
        p.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH, PathUtils.getTemplatesPath());

        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.init(p);

        VelocityContext context = new VelocityContext();
        for (String key : datas.keySet()) {
            context.put(key, datas.get(key));
        }

        StringWriter writer = new StringWriter();
        Template template = velocityEngine.getTemplate(templateName);
        template.merge(context, writer);

        RestfulResult result = new RestfulResult();
        result.setData(writer.toString());

        String callbackName = httpServletRequest.getParameter("callbackName");
        return new JSONPObject(callbackName, result);
    }

    protected String generateToken() {
        String remoteHost = httpServletRequest.getRemoteHost();
        long time = System.currentTimeMillis();
        Random random = new Random();
        int r = random.nextInt(1000);
        StringBuilder token = new StringBuilder();
        token.append(time);
        token.append(remoteHost.hashCode());
        token.append(r);
        return token.toString();
    }
}
