package com.example.calculator.config;

import com.example.calculator.security.ApiKeyFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfiguration {

    @Bean
    FilterRegistrationBean<ApiKeyFilter> apiKeyFilterRegistration(AppProperties properties) {
        var registration = new FilterRegistrationBean<>(new ApiKeyFilter(properties.apiKey()));
        registration.addUrlPatterns("/calculator/v1/*");
        registration.setOrder(1);
        return registration;
    }
}
