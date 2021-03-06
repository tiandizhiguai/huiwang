package com.huiwang.controller.user;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.huiwang.constant.Constants;
import com.huiwang.controller.AbstractController;
import com.huiwang.param.UserParam;
import com.huiwang.service.UserService;
import com.huiwang.vo.User;

/**
 * 用户信息模型。
 * 
 * @author Administrator 2015年8月8日 下午9:55:25
 */
@Controller
@RequestMapping("/user")
public class UserController extends AbstractController {

	@Resource
	private UserService userServie;

    @RequestMapping("/preRegister")
    public ModelAndView preRegister(UserParam param) {
        String token = this.generateToken();
        httpSession.setAttribute(Constants.TOKEN_KEY, token);
        ModelAndView modelAndView = getModelAndView();
        modelAndView.addObject("tokenValue", token);
        modelAndView.setViewName("/user/preRegister");
        return modelAndView;
    }

    @RequestMapping("/register")
    public ModelAndView register(UserParam param) {

        ModelAndView modelAndView = getModelAndView();
        String token = (String) httpSession.getAttribute(Constants.TOKEN_KEY);

        // 1.无效操作
        if (StringUtils.isEmpty(param.getToken()) || StringUtils.isEmpty(token)) {
            modelAndView.setViewName("/error/invalidSubmit");
            return modelAndView;
        }

        // 2.重复操作
        if (Constants.TOKEN_KEY.equals(token)) {
            modelAndView.setViewName("/error/repeatSubmit");
            return modelAndView;
        }

        // 3.无效操作
        if (!token.equals(param.getToken())) {
            modelAndView.setViewName("/error/invalidSubmit");
            return modelAndView;
        }

        if (StringUtils.isEmpty(param.getLoginName()) || StringUtils.isEmpty(param.getPasswd())) {
            modelAndView.setViewName("/user/registerError");
            return modelAndView;
        }

        userServie.add(param);
        User loginUser = userServie.get(param.getLoginName());
        if (loginUser != null && StringUtils.endsWithIgnoreCase(param.getLoginName(), loginUser.getLoginName())) {
            httpSession.setAttribute(Constants.LOGIN_USER, loginUser);
            modelAndView.setViewName("/user/registerSuccess");
        } else {
            modelAndView.setViewName("/user/registerError");
        }

        httpSession.setAttribute(Constants.TOKEN_KEY, Constants.TOKEN_KEY);

        return modelAndView;
    }

    @RequestMapping("/preLogin")
    public ModelAndView preLogin(String redirectUri) {
        ModelAndView modelAndView = getModelAndView();
        modelAndView.addObject("redirectUri", redirectUri);
        modelAndView.setViewName("/user/preLogin");

        return modelAndView;
    }

    @RequestMapping("/login")
	public ModelAndView login(UserParam param) {
        ModelAndView modelAndView = getModelAndView();
        User loginUser = userServie.get(param);
        if (loginUser != null && StringUtils.endsWithIgnoreCase(param.getLoginName(), loginUser.getLoginName())) {
            // 更新登陆时间
            param.setPasswd(null);
            userServie.updateLoginTime(param);
            httpSession.setAttribute(Constants.LOGIN_USER, loginUser);

            // 创建存放了登陆数据返回模型
            modelAndView = getModelAndView();
            if (!StringUtils.isEmpty(param.getRedirectUri())) {
                modelAndView.setViewName("redirect:" + param.getRedirectUri());
            }else{
                modelAndView.setViewName("redirect:/index");
            }
		}else{
            modelAndView.setViewName("/user/loginError");
		}
		return modelAndView;
	}

    @RequestMapping("/logout")
    public ModelAndView logout(UserParam param) {
        httpSession.removeAttribute(Constants.LOGIN_USER);
        ModelAndView modelAndView = getModelAndView();
        modelAndView.setViewName("redirect:/index");
        return modelAndView;
    }

    @RequestMapping("/preUserIdea")
    public ModelAndView preUserIdea() {
        ModelAndView modelAndView = getModelAndView();
        modelAndView.setViewName("/user/preUserIdea");
        return modelAndView;
    }
}