package com.offcn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author 13320
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class PeMobileApplication {
    public static void main(String[] args) {
        SpringApplication.run(PeMobileApplication.class);
    }
}
