package com.huge.feign.controller;


import com.huge.feign.service.DeptFeignService;
import com.huge.feign.service.DeptHystrixService;
import com.huge.provider.entity.Dept;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author huge1988
 * @date 2023/6/28 - 14:26
 */
@Slf4j
@RestController
@DefaultProperties(defaultFallback = "dept_Global_FallbackMethod") //全局的服务降级方法
public class Controller {

    @Autowired
    private DeptFeignService service;

    @Autowired
    private DeptHystrixService hystrixService;

    @GetMapping("/consumer/dept/get")
    public Dept get() {
        return service.get();
    }


    @GetMapping("/consumer/dept/list")
    public List<Dept> getList() {
        return service.getList();
    }

    @RequestMapping(value = "/consumer/dept/feign/timeout")
    public String deptFeignTimeout() {
        // openFeign-ribbon 客户端一般默认等待一秒钟，超过该时间就会报错
        return service.deptFeignTimeout();
    }

    //在客户端进行降级 降级（FallBack）方法必须与其对应的业务方法在同一个类中，否则无法生效。
    @RequestMapping(value = "/consumer/dept/hystrix/timeout/{id}")
    @HystrixCommand //为该请求指定专属的回退方法(fallbackMethod = "dept_TimeoutHandler")
    public String deptInfo_Timeout(@PathVariable("id") Integer id) {
        String s = hystrixService.deptInfo_Timeout(id);
        log.info(s);
        return s;
    }

    // deptInfo_Timeout方法的 专用 fallback 方法
    public String dept_TimeoutHandler(@PathVariable("id") Integer id) {
        log.info("deptInfo_Timeout 出错，服务已被降级！");
        return "服务端系统繁忙，请稍后再试！（客户端 deptInfo_Timeout 专属的回退方法触发）";
    }

    /**
     * 全局的 fallback 方法，
     * 降级（FallBack）方法必须与其对应的业务方法在同一个类中，否则无法生效。
     *
     * @DefaultProperties(defaultFallback = "dept_Global_FallbackMethod") 类上注解，请求方法上使用 @HystrixCommand 注解
     */
    public String dept_Global_FallbackMethod() {
        return "运行出错或服务端系统繁忙，请稍后再试！（客户端全局回退方法触发,）";
    }

}
