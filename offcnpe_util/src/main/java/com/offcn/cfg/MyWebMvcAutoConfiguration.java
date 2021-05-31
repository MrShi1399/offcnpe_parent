package com.offcn.cfg;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author 13320
 */
@Configuration
public class MyWebMvcAutoConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/setmeal/**")
                .addResourceLocations("file:E:/code/uploadfile/offcnpe/img/")
                .addResourceLocations("file:E:/code/uploadfile/offcnpe/excel/");
    }
}
