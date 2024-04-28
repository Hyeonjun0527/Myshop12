package myshop12.com.model2.mvc;

import lombok.RequiredArgsConstructor;
import myshop12.com.model2.mvc.common.web.LogonCheckInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@RequiredArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
//    @Autowired
//    LogonCheckInterceptor logonCheckInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(logonCheckInterceptor)
//                .addPathPatterns("/**") // 모든 경로에 대해 인터셉터 적용
//                .excludePathPatterns("/rest/**")
//                .excludePathPatterns("/user/json/login")
//                .excludePathPatterns("/login", "/loginView", "/error"); // 로그인 관련 경로와 에러 페이지는 제외
//    }
    @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedOrigins("http://127.0.0.1:3000","http://192.168.0.65:3000","http://localhost:3000")
                    .allowedMethods(HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.PUT.name(), HttpMethod.DELETE.name(), HttpMethod.OPTIONS.name())
                    .allowedHeaders("Content-Type", "Authorization", "Accept", "X-Requested-With", "Cache-Control", "Pragma", "Origin", "Referer", "User-Agent", "Sec-Ch-Ua", "Sec-Ch-Ua-Mobile", "Sec-Ch-Ua-Platform") // 허용할 헤더
                    .allowCredentials(true) // 쿠키 등의 크레덴셜 허용
                    .maxAge(3600); // 사전 요청 캐시 시간 (초 단위)

    }
}
