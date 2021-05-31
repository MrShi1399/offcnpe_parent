package com.offcn.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置访问文件服务器上图片
 * @author 13320
 */
@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    @Value("${imgFilePath}")
    private String imgFilePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if(imgFilePath.equals("") || imgFilePath.equals("${imagesPath}")){
            String imagesPath = WebAppConfig.class.getClassLoader().getResource("").getPath();
            if(imagesPath.indexOf(".jar")>0){
                imagesPath = imagesPath.substring(0, imagesPath.indexOf(".jar"));
            }else if(imagesPath.indexOf("classes")>0){
                imagesPath = "file:"+imagesPath.substring(0, imagesPath.indexOf("classes"));
            }
            imagesPath = imagesPath.substring(0, imagesPath.lastIndexOf("/"))+"/images/";
            imgFilePath = imagesPath;
        }
        //addResourceLocations方法中的file代表的是文件协议
        registry.addResourceHandler("/images/**").addResourceLocations("file:"+imgFilePath);

    }
}
