package com.project.hammer.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorRegisterConfig implements WebMvcConfigurer {

    private EcomInterceptor ecomInterceptor;

    public InterceptorRegisterConfig(final EcomInterceptor ecomInterceptor){
        this.ecomInterceptor=ecomInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(ecomInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/none");
    }


}
