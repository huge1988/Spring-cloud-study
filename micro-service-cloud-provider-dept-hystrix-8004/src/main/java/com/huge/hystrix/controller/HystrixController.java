package com.huge.hystrix.controller;

import com.huge.hystrix.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huge1988
 * @date 2023/6/28 - 17:58
 */
@Slf4j
@RestController
public class HystrixController {

    @Autowired
    private DeptService service;

    @Value("${server.port}")
    private String serverPort;


    // Hystrix 服务超时降级
    @RequestMapping(value = "/dept/hystrix/timeout/{id}")
    public String deptInfo_Timeout(@PathVariable("id") Integer id) {
        String result = service.deptInfoTimeout(id);
        log.info("端口号：" + serverPort + " result:" + result);
        return result + "，   端口号：" + serverPort;
    }

    // Hystrix 服务熔断
    @RequestMapping(value = "/dept/hystrix/circuit/{id}")
    public String deptCircuitBreaker(@PathVariable("id") Integer id){
        String result = service.deptCircuitBreaker(id);
        log.info("result:"+result);
        return result;
    }


}
