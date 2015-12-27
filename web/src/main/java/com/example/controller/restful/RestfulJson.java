package com.example.controller.restful;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.controller.AbstractController;
import com.example.param.CityParam;
import com.example.param.TopicParam;
import com.example.param.UserParam;
import com.example.service.CityService;
import com.example.service.ProvinceService;
import com.example.service.TopicService;
import com.example.service.UserService;
import com.example.vo.CityVO;
import com.example.vo.ProvinceVO;
import com.example.vo.TopicVO;
import com.example.vo.UserVO;

@Controller
@RequestMapping("/json")
public class RestfulJson extends AbstractController {

    @Resource
    private UserService     userServie;

    @Resource
    private ProvinceService provinceService;

    @Resource
    private CityService     cityServie;

    @Resource
    private TopicService    topicService;

    @ResponseBody
    @RequestMapping("/getTopics")
    public List<TopicVO> getUser(TopicParam param) {
        List<TopicVO> vos = topicService.getList(param);
        return vos;
    }

    @ResponseBody
    @RequestMapping("/loginNameExists")
    public boolean loginNameExists(UserParam param) {
        UserVO user = userServie.get(param);
        return user != null && StringUtils.equals(param.getLoginName(), user.getLoginName());
    }

    @ResponseBody
    @RequestMapping("/checkPassword")
    public boolean checkPassword(UserParam param) {
        UserVO user = userServie.get(param);
        return StringUtils.equals(param.getPasswd(), user.getPasswd());
    }

    @ResponseBody
    @RequestMapping("/getCities")
    public List<CityVO> getCities(CityParam param) {
        return cityServie.getList(param);
    }

    @ResponseBody
    @RequestMapping("/getAllProvince")
    public List<ProvinceVO> getAllProvince() {
        return provinceService.getAll();
    }

}
