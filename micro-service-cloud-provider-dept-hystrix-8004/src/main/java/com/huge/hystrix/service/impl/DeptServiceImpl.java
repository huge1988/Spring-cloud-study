package com.huge.hystrix.service.impl;


import com.huge.hystrix.service.DeptService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.cloud.commons.util.IdUtils;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author huge1988
 * @date 2023/6/28 - 17:50
 */
@Service("deptService")
public class DeptServiceImpl implements DeptService {


    @Override
    public String deptInfoOk(Integer id) {
        return "线程池：" + Thread.currentThread().getName() + "  deptInfo_Ok,id:   " + id;
    }

    //我们可以看到 deptInfoTimeout() 方法上使用 @HystrixCommand 注解，该注解说明如下：
    //参数 fallbackMethod 属性用于指定降级方法。
    //参数 execution.isolation.thread.timeoutInMilliseconds 用于设置自身调用超时时间的峰值，峰值内可以正常运行，否则执行降级方法
    //一旦该方法失败并抛出了异常信息后，会自动调用  @HystrixCommand 注解标注的 fallbackMethod 指定的方法
    @HystrixCommand(fallbackMethod = "dept_TimeoutHandler",
            commandProperties =
                    //规定 5 秒钟以内就不报错，正常运行，超过 5 秒就报错，调用指定的方法
                    {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")})
    @Override
    public String deptInfoTimeout(Integer id) {
        int outTime = 4;
        try {
            TimeUnit.SECONDS.sleep(outTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "线程池：" + Thread.currentThread().getName() + "  deptInfo_Timeout,id:   " + id + "  耗时: " + outTime;
    }

    /**
     * Hystrix 熔断案例
     *
     * @param id
     * @return
     */
    @Override
    @HystrixCommand(fallbackMethod = "deptCircuitBreaker_fallback", commandProperties = {
            //以下参数在 HystrixCommandProperties 类中有默认配置
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"), //是否开启熔断器
            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000"), //统计时间窗
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"), //统计时间窗内请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"), //休眠时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"), //在统计时间窗口期以内，请求失败率达到 60% 时进入熔断状态
    })
    public String deptCircuitBreaker(Integer id) {
        if (id < 0) {
            //当传入的 id 为负数时，抛出异常，调用降级方法
            throw new RuntimeException("id 不能是负数！");
        }
        long l = System.currentTimeMillis();
        String serialNum = l + ":" + id;
        return Thread.currentThread().getName() + "\t" + "调用成功，流水号为：" + serialNum;
    }

    // 当服务出现故障后，调用该方法给出友好提示
    public String dept_TimeoutHandler(Integer id) {
        return "系统繁忙请稍后再试！" + "线程池：" + Thread.currentThread().getName() + "  deptInfo_Timeout,id:   " + id;
    }

    //deptCircuitBreaker 的降级方法
    public String deptCircuitBreaker_fallback(Integer id) {
        return "id 不能是负数,请稍后重试!\t id:" + id;
    }
}
