package com.offcn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 仅作环境测试
 * @author 13320
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class PeControllerApplication {
    public static void main(String[] args) {
        SpringApplication.run(PeControllerApplication.class);
    }
}
