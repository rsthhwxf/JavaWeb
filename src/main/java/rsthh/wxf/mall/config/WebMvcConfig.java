package rsthh.wxf.mall.config;

import org.mybatis.spring.annotation.MapperScan;
import rsthh.wxf.mall.interceptor.TokenInterceptor;
import rsthh.wxf.mall.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@MapperScan(basePackages = "rsthh.wxf.mall")
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {



    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new LoginInterceptor());
        registry.addInterceptor(new TokenInterceptor());
    }

}