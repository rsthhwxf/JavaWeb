package rsthh.wxf.mall.Interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import rsthh.wxf.mall.utils.ThreadLocalUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ThreadLocalInterceptor implements HandlerInterceptor {

    /**
     * 请求执行前执行的，将用户信息放入ThreadLocal
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    /**
     * 接口访问结束后，从ThreadLocal中删除用户信息
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalUtil.clear();
    }

}