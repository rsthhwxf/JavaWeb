package rsthh.wxf.mall.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import rsthh.wxf.mall.po.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI();
        System.out.println("*"+url);
        if("/admin/login/login".equals(url) || "/admin/login/toLogin".equals(url))return true;
        else if(url.startsWith("/admin")){
            HttpSession session = request.getSession();
            User User = (User) session.getAttribute("User");
            if (null == User) {
                response.sendRedirect(request.getContextPath() + "/admin/login/login");
                return false;
            }
        }
        return true;
    }
}
