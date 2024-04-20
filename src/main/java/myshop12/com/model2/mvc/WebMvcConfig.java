package myshop12.com.model2.mvc;

import myshop12.com.model2.mvc.common.web.LogonCheckInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    LogonCheckInterceptor logonCheckInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logonCheckInterceptor)
                .addPathPatterns("/**") // 모든 경로에 대해 인터셉터 적용
                .excludePathPatterns("/login", "/loginView", "/error"); // 로그인 관련 경로와 에러 페이지는 제외
    }
    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins("http://127.0.0.1:3000/")
                .allowedMethods("GET","POST","PUT","DELETE")
                .allowedHeaders("Content-Type", "Authorization", "Accept", "X-Requested-With", "Cache-Control", "Pragma", "Origin", "Cookie")
                .allowCredentials(true)
                .maxAge(3600);
    }

}
