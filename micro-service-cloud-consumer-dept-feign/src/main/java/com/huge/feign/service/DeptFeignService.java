package com.huge.feign.service;


import com.huge.provider.entity.Dept;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


/**
 * @author huge1988
 * @date 2023/6/28 - 14:21
 */
@Component
@FeignClient(value = "MICROSERVICECLOUDPROVIDERDEPT")
public interface DeptFeignService {

    @RequestMapping(value = "/dept/get", method = RequestMethod.GET)
    Dept get();

    @RequestMapping(value = "/dept/list", method = RequestMethod.GET)
    List<Dept> getList();

    @RequestMapping(value = "/dept/feign/timeout")
    String deptFeignTimeout();

    @RequestMapping(value = "/dept/hystrix/timeout/{id}")
    String deptInfo_Timeout(@PathVariable("id") Integer id);
}
