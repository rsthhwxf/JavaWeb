package rsthh.wxf.mall.interceptor;

import com.alibaba.fastjson.JSONObject;

import rsthh.wxf.mall.utils.TokenUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI();
        System.out.println(url);
        if("/user/login".equals(url)||"/user/register".equals(url))return true;
        response.setCharacterEncoding("utf-8");
        String token = request.getHeader("token");
        if (token != null) {
            boolean result = TokenUtil.validate(token);
            if (result) {
                System.out.println("通过拦截器");
                return true;
            }
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        JSONObject json = new JSONObject();
        json.put("msg", "token验证失败!");
        json.put("status", "1");
        json.put("data", "");
        response.getWriter().append(json.toJSONString());
        System.out.println("认证失败，未通过拦截器");
        return false;
    }
}