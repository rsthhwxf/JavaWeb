package com.liasplf.ncumall.interceptor;

import com.liasplf.ncumall.po.Manage;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI();
        if("/admin/login/login".equals(url) || "/admin/login/toLogin".equals(url))return true;
        else if(url.startsWith("/admin")){
            HttpSession session = request.getSession();
            Manage manage = (Manage) session.getAttribute("manage");
            if (null == manage) {
                response.sendRedirect(request.getContextPath() + "/admin/login/login");
                return false;
            }
        }
        return true;
    }
}
