package com.huge.hystrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author huge1988
 * @date 2023/6/28 - 17:58
 */
@EnableCircuitBreaker //激活熔断器功能
@EnableEurekaClient //开启 Eureka 客户端功能
@SpringBootApplication
public class HysTrixApplication {

    public static void main(String[] args) {
        SpringApplication.run(HysTrixApplication.class, args);
    }
}
