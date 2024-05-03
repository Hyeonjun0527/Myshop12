package myshop12.com.model2.mvc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class PropertySourcesConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(){
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        configurer.setLocations(
                new ClassPathResource("config/common.properties"),
                new ClassPathResource("config/apiKey.properties"),
                new ClassPathResource("config/jdbc.properties"),
                new ClassPathResource("config/log4j.properties"),
                new ClassPathResource("config/myIp.properties")
        );
        return configurer;
    }
}
