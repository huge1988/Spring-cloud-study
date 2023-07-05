package com.huge.provider3.controller;


import com.huge.provider.entity.Dept;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * @author huge1988
 * @date 2023/6/28 - 14:31
 */
@RestController
public class Controller {

    @Value("${server.port}")
    private String serverPort;

    private final static String service = "provider-dept-8003";

    @RequestMapping(value = "/dept/get", method = RequestMethod.GET)
    public Dept get() {
        return new Dept(1, "huge", "huge-spring-cloud", service);
    }

    @RequestMapping(value = "/dept/list", method = RequestMethod.GET)
    public List<Dept> list() {
        List<Dept> depts = new ArrayList<>();
        depts.add(new Dept(1, "huge", "huge-spring-cloud", service));
        depts.add(new Dept(2, "xin", "xin-1995-06-13", service));
        depts.add(new Dept(3, "broken", "broken-2018-07-16", service));
        return depts;
    }

    /**
     * 超时测试,该服务的响应时间为 5 秒
     */
    @RequestMapping(value = "/dept/feign/timeout")
    public String DeptFeignTimeout() {
        //暂停 5 秒
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return serverPort;
    }

}
