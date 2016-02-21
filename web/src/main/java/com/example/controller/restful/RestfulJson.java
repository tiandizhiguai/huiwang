package com.example.controller.restful;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.controller.AbstractController;
import com.example.param.ArticleCareParam;
import com.example.param.ArticlePraiseParam;
import com.example.param.CityParam;
import com.example.param.TopicParam;
import com.example.param.UserIdeaParam;
import com.example.param.UserParam;
import com.example.result.RestfulResult;
import com.example.service.ArticleCareService;
import com.example.service.ArticlePraiseService;
import com.example.service.ArticleService;
import com.example.service.ArticleStatisService;
import com.example.service.CityService;
import com.example.service.ProvinceService;
import com.example.service.TopicService;
import com.example.service.UserIdeaService;
import com.example.service.UserService;
import com.example.vo.City;
import com.example.vo.Province;
import com.example.vo.Topic;
import com.example.vo.User;

@Controller
@RequestMapping("/json")
public class RestfulJson extends AbstractController {

    @Resource
    private UserService          userServie;

    @Resource
    private ProvinceService      provinceService;

    @Resource
    private CityService          cityServie;

    @Resource
    private TopicService         topicService;

    @Resource
    private ArticleService       articleService;

    @Resource
    private ArticleCareService   articleCareService;

    @Resource
    private ArticlePraiseService articlePraiseService;

    @Resource
    private ArticleStatisService articleStatisService;

    @Resource
    private UserIdeaService      userIdeaService;

    @ResponseBody
    @RequestMapping("/getTopics")
    public List<Topic> getUser(TopicParam param) {
        List<Topic> vos = topicService.getList(param);
        return vos;
    }

    @ResponseBody
    @RequestMapping("/loginNameExists")
    public boolean loginNameExists(UserParam param) {
        User user = userServie.get(param);
        return user != null && StringUtils.equals(param.getLoginName(), user.getLoginName());
    }

    @ResponseBody
    @RequestMapping("/checkPassword")
    public boolean checkPassword(UserParam param) {
        User user = userServie.get(param);
        return user != null;
    }

    @ResponseBody
    @RequestMapping("/getCities")
    public List<City> getCities(CityParam param) {
        return cityServie.getList(param);
    }

    @ResponseBody
    @RequestMapping("/getAllProvince")
    public List<Province> getAllProvince() {
        return provinceService.getAll();
    }

    @ResponseBody
    @RequestMapping("/careArticle")
    public RestfulResult careArticle(ArticleCareParam param) {
        RestfulResult result = new RestfulResult();
        if (!this.isUserLogined()) {
            return result;
        }

        if (param.getArticleId() == null || param.getArticleId() == 0 || param.getUserId() == null
            || param.getUserId() == 0) {
            return result;
        }
        result.setData(articleStatisService.careArticle(param));
        return result;
    }

    @ResponseBody
    @RequestMapping("/praiseArticle")
    public RestfulResult praiseArticle(ArticlePraiseParam param) {
        RestfulResult result = new RestfulResult();

        if (!this.isUserLogined()) {
            return result;
        }

        if (param.getArticleId() == null || param.getArticleId() == 0 || param.getUserId() == null
            || param.getUserId() == 0) {
            return result;
        }
        result.setData(articleStatisService.praiseArticle(param));
        return result;
    }

    @ResponseBody
    @RequestMapping("/addUserIdea")
    public RestfulResult addUserIdea(UserIdeaParam param) {
        RestfulResult result = new RestfulResult();
        if (StringUtils.isEmpty(param.getContent())) {
            result.setErrorMsg("content is empty");
            return result;
        }
        if (this.isUserLogined()) {
            param.setId(this.getLoginedUser().getId());
        }
        userIdeaService.add(param);
        return result;
    }
}
