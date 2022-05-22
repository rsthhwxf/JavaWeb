package rsthh.wxf.mall.filter;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.github.pagehelper.util.StringUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import rsthh.wxf.mall.po.Token;

import rsthh.wxf.mall.service.UserService;
import rsthh.wxf.mall.utils.Consts;
import rsthh.wxf.mall.utils.JwtUtil;
import rsthh.wxf.mall.utils.ThreadLocalUtil;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class AuthFilter extends AuthenticatingFilter {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;//将token保存到redis


    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String token = httpServletRequest.getHeader("token");
        if(StringUtil.isEmpty(token))return null;
        return new Token(token);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest request  = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader("Content-Type","text/html;charset=UTF-8");
        //允许跨域请求
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Origin", response.getHeader("Origin"));
        //从请求中获取令牌
        String tokenStr = request.getHeader("token");
        //如果令牌为空，在响应中返回前端相关信息
        JSONObject json = new JSONObject();
        System.out.println(tokenStr);
        if(StringUtil.isEmpty(tokenStr)){
            json.put("msg", "token验证失败!");
            json.put("status", "1");
            json.put("data", "");
            response.getWriter().append(json.toJSONString());
            return false;
        }
        //如果令牌存在，刷新令牌
        try{
            //校验令牌
            JwtUtil.validate(tokenStr);
        }catch (TokenExpiredException e) { //发现令牌过期

            //去redis中找
            if (redisTemplate.hasKey("token")) {
                //redis存在，重新为客户生成一个新的token
                redisTemplate.delete("token");
                int userId = JwtUtil.getIDByToken(tokenStr);
                String token = JwtUtil.getToken(userId);
                redisTemplate.opsForValue().set(token, userId + token, 7, TimeUnit.DAYS);
                response.setHeader("token",token);
            }
            //redis令牌不存在,需要重新登录
            else {
                response.setStatus(1);
                response.getWriter().print("令牌已经过期");
                return false;
            }

        }catch (JWTDecodeException e){
            response.setStatus(1);
            response.getWriter().print("无效的令牌");
            return false;
        }
        SetOperations<String, String> set = redisTemplate.opsForSet();
        if(set.isMember("blackList", tokenStr)){
            response.setStatus(1);
            response.getWriter().print("无效的令牌");
            return false;
        }
        boolean result = executeLogin(request, response);
        if(System.currentTimeMillis()-JwtUtil.getTimeByToken(tokenStr).getTime() < Consts.expire / 2){
            redisTemplate.delete("token");
            int userId = JwtUtil.getIDByToken(tokenStr);
            String token = JwtUtil.getToken(userId);
            redisTemplate.opsForValue().set(token, userId + "", 7, TimeUnit.DAYS);
        }
        if(ThreadLocalUtil.getUser()==null)ThreadLocalUtil.setUser(userService.getById(JwtUtil.getIDByToken(tokenStr)));
        return result;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String url = httpServletRequest.getRequestURI();
        if("/user/login".equals(url)||"/user/register".equals(url))return true;
        return false;
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=utf-8");
        JSONObject json = new JSONObject();
        json.put("msg", "token验证失败!");
        json.put("status", "1");
        json.put("data", "");
        try {
            resp.getWriter().append(json.toJSONString());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;

    }
}
