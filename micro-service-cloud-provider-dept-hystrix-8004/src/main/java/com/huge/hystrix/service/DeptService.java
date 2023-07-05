package com.huge.hystrix.service;

import org.springframework.stereotype.Service;

/**
 * @author huge1988
 * @date 2023/6/28 - 17:49
 */
@Service
public interface DeptService {

    // hystrix 熔断器示例 ok
    String deptInfoOk(Integer id);

    //hystrix 熔断器超时案例
    String deptInfoTimeout(Integer id);

    // Hystrix 熔断机制案例
    String deptCircuitBreaker(Integer id);
}
