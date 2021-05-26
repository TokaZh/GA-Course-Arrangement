package com.tokaku.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//登录控制
public class LoginHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //登陆成功之后
        Object loginUser = request.getSession().getAttribute("Role");

        if (loginUser == null) {
            request.setAttribute("msg", "请先登录！");
            request.getRequestDispatcher("/login").forward(request, response);
            return false;
        } else
            return true;
    }
}
