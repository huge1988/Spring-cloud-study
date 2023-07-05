package com.huge.feign.service;


import org.springframework.stereotype.Component;

/**
 * @author huge1988
 * @date 2023/6/30 - 17:20
 *
 *  Hystrix 服务降级
 *  解耦回退逻辑 统一为 DeptHystrixService 中的方法提供服务降级处理
 *
 */
@Component
public class DeptHystrixFallBackService implements DeptHystrixService {

    @Override
    public String deptInfo_Ok(Integer id) {
        return "-----系统繁忙，请稍后重试！（解耦回退方法触发）-----";
    }

    @Override
    public String deptInfo_Timeout(Integer id) {
        return "-----系统繁忙，请稍后重试！（解耦回退方法触发）-----";
    }
}
