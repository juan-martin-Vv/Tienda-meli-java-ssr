package com.jmvv.MpShop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class mvcConfig implements WebMvcConfigurer{
    // private ApplicationContext applicationContext;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/store");
        registry.addViewController("/store").setViewName("layout/home");
        registry.addViewController("/store/success").setViewName("layout/succes");
        registry.addViewController("/login").setViewName("layout/loginForm");
        registry.addViewController("/auth/hooks").setViewName("layout/hooksData");
        WebMvcConfigurer.super.addViewControllers(registry);
    }

}
