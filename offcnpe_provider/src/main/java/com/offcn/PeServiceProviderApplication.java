package com.offcn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 仅作环境测试
 * @author 13320
 */
@SpringBootApplication
@MapperScan("com.offcn.mapper")
public class PeServiceProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(PeServiceProviderApplication.class);
    }
}
