package myshop12.com.model2.mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@EnableAspectJAutoProxy
@ComponentScan(
        basePackages = {"myshop12.com.model2.mvc.**","myshop12.com.model2.mvc"},
        includeFilters = {
                @ComponentScan.Filter(type= FilterType.ANNOTATION, classes = {
                        org.springframework.stereotype.Controller.class,
                        org.springframework.web.bind.annotation.RestController.class,
                        org.springframework.stereotype.Service.class,
                        org.springframework.stereotype.Repository.class,
                        org.springframework.context.annotation.Configuration.class,
                        org.springframework.context.annotation.Bean.class
                }),

        })//첫번째 scan할것, 두번째 @Configuration
public class Myshop12Application {

    public static void main(String[] args) {
        SpringApplication.run(Myshop12Application.class, args);
    }

}
