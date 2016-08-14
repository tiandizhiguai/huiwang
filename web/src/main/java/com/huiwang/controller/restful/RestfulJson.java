package com.huiwang.controller.restful;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.huiwang.constant.Constants;
import com.huiwang.constant.SortType;
import com.huiwang.constant.StatusType;
import com.huiwang.controller.AbstractController;
import com.huiwang.param.ArticleCareParam;
import com.huiwang.param.ArticleCommentParam;
import com.huiwang.param.ArticlePraiseParam;
import com.huiwang.param.CityParam;
import com.huiwang.param.CommentMessageParam;
import com.huiwang.param.TopicParam;
import com.huiwang.param.UserIdeaParam;
import com.huiwang.param.UserParam;
import com.huiwang.result.RestfulResult;
import com.huiwang.service.ArticleCareService;
import com.huiwang.service.ArticleCommentService;
import com.huiwang.service.ArticlePraiseService;
import com.huiwang.service.ArticleService;
import com.huiwang.service.ArticleStatisService;
import com.huiwang.service.CityService;
import com.huiwang.service.CommentMessageService;
import com.huiwang.service.ProvinceService;
import com.huiwang.service.TopicService;
import com.huiwang.service.UserIdeaService;
import com.huiwang.service.UserService;
import com.huiwang.service.biz.ArticleBizService;
import com.huiwang.vo.City;
import com.huiwang.vo.Province;
import com.huiwang.vo.Topic;
import com.huiwang.vo.User;

@Controller
@RequestMapping("/json")
public class RestfulJson extends AbstractController {

    @Resource
    private UserService           userServie;

    @Resource
    private ProvinceService       provinceService;

    @Resource
    private CityService           cityServie;

    @Resource
    private TopicService          topicService;

    @Resource
    private ArticleService        articleService;

    @Resource
    private ArticleCareService    articleCareService;

    @Resource
    private ArticlePraiseService  articlePraiseService;

    @Resource
    private ArticleStatisService  articleStatisService;

    @Resource
    private UserIdeaService       userIdeaService;

    @Resource
    private ArticleCommentService articleCommentService;

    @Resource
    private ArticleBizService     articleBizService;

    @Resource
    private CommentMessageService commentMessageService;

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

    @ResponseBody
    @RequestMapping("/getComments")
    public JSONPObject getComments(ArticleCommentParam param) {
        param.setSortType(SortType.ID.getValue());
        param.setDescOrder(false);
        param.setStatus(StatusType.NORMAL.getValue());
        param.setPageSize(10);
        int totalCount = articleCommentService.getCount(param);
        int totalPage = totalCount / param.getPageSize();
        if (totalCount % param.getPageSize() != 0) {
            totalPage++;
        }

        Map<String, Object> datas = new HashMap<String, Object>();
        datas.put("totalCount", totalCount);
        datas.put("pageNo", param.getPageNo());
        datas.put("totalPage", totalPage);
        datas.put("datas", articleBizService.getCommentList(param));
        return getJsonpData(datas, "article/articleComment.vm");
    }

    @ResponseBody
    @RequestMapping("/getArticleDetail")
    public RestfulResult getArticleDetail(Long artcileId) {
        RestfulResult result = new RestfulResult();
        result.setData(articleBizService.getDetail(null, artcileId));
        return result;
    }

    @ResponseBody
    @RequestMapping("/uploadArticleImg")
    public String uploadArticleImg(@RequestParam MultipartFile[] imgFile) {

        // 创建文章图片目录
        File parentPath = new File(Constants.ARTICLE_IMG_ABSOLUTE_PATH);
        if (!parentPath.exists()) {
            parentPath.mkdir();
        }

        List<String> localFileNames = saveFiles(imgFile, parentPath);
        String imgName = null;
        if (CollectionUtils.isNotEmpty(localFileNames)) {
            imgName = localFileNames.get(0);
        }

        JSONObject obj = new JSONObject();
        obj.put("error", 0);
        obj.put("url", Constants.STYLE_DOMAIN_URI_WHITH_HTTP + "img/article/" + imgName);
        return obj.toString();
    }

    @ResponseBody
    @RequestMapping("/autocompleteuser")
    public RestfulResult autocompleteuser(String beginRealName) {
        RestfulResult result = new RestfulResult();
        UserParam param = new UserParam();
        param.setBeginRealName(beginRealName);
        result.setData(userServie.getList(param));
        return result;
    }

    @ResponseBody
    @RequestMapping("/updateCommentMessage")
    public RestfulResult updateCommentMessage(CommentMessageParam param) {
        RestfulResult result = new RestfulResult();
        if (param.getId() == null) {
            result.setErrorMsg("请指定要修改的评论");
            return result;
        }

        if (!this.isUserLogined()) {
            result.setErrorMsg("用户没有登陆");
            return result;
        }

        commentMessageService.update(param);

        return result;
    }
}
