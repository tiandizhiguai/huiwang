package com.huiwang.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.huiwang.constant.Constants;

public class LoginUserFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
                                                                                             ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        if (session == null || session.getAttribute(Constants.LOGIN_USER) == null) {
            Enumeration<String> paramNames = httpRequest.getParameterNames();
            StringBuilder queryString = new StringBuilder("?");
            for (; paramNames.hasMoreElements();) {
                String paramName = paramNames.nextElement();
                String paramValue = httpRequest.getParameter(paramName);
                queryString.append(paramName);
                queryString.append("=");
                queryString.append(paramValue);
                queryString.append("&");
            }
            if (queryString.length() > 1) {
                queryString.deleteCharAt(queryString.length() - 1);
            }

            httpResponse.sendRedirect("/user/preLogin?redirectUri=" + httpRequest.getRequestURI() + queryString);
        } else {
            chain.doFilter(httpRequest, httpResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
