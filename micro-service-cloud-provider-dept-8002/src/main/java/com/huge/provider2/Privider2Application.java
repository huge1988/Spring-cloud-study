package com.huge.provider2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author huge1988
 * @date 2023/6/28 - 15:38
 */

@EnableEurekaClient
@SpringBootApplication
public class Privider2Application {

    public static void main(String[] args) {
        SpringApplication.run(Privider2Application.class, args);
    }
}
