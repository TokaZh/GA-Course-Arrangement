package com.tokaku.config;

import org.springframework.web.servlet.HandlerInterceptor;

//登录控制
public class LoginHandlerInterceptor implements HandlerInterceptor {
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//
//        //登陆成功之后
//        Object loginUser = request.getSession().getAttribute("loginUser");
//
//        if(loginUser==null){
//            request.setAttribute("msg","没有登录");
//            request.getRequestDispatcher("/login").forward(request,response);
//            return false;
//        }else
//            return true;
//    }
}
