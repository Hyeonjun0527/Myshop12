package myshop12.com.model2.mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableAspectJAutoProxy
@ComponentScan(
        basePackages = {"myshop12.com.model2.mvc.**","myshop12.com.model2.mvc"},
        includeFilters = {
                @ComponentScan.Filter(type= FilterType.ANNOTATION, classes = {
                        //컴포넌트도
                        org.springframework.stereotype.Component.class,
                        org.springframework.stereotype.Controller.class,
                        org.springframework.web.bind.annotation.RestController.class,
                        org.springframework.stereotype.Service.class,
                        org.springframework.stereotype.Repository.class,
                        org.springframework.context.annotation.Configuration.class,
                        org.springframework.context.annotation.Bean.class
                }),

        })//첫번째 scan할것, 두번째 @Configuration
@EnableJpaRepositories("myshop12.com.model2.mvc.test.Repository")
//서블릿 이니셜라이저는 web.xml 역할 수행할 수 있다.
public class Myshop12Application extends SpringBootServletInitializer {

        public static void main(String[] args) {
            SpringApplication.run(Myshop12Application.class, args);
        }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Myshop12Application.class);
    }

}
