package com.test.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TestService {

    private AtomicInteger count = new AtomicInteger(0);

    @SentinelResource(value = "flow", blockHandler = "testHandler", fallback = "degradeFallback")
    public String flow(){
        System.out.println("------正常执行flow方法");
        if(count.incrementAndGet() % 3 == 0) {
            throw new RuntimeException("抛出业务异常");
        }
        return "执行flow方法";
    }
    public String testHandler(BlockException ex){

        System.out.println("------触发流控策略!"+ex);
        return "执行流控方法";
    }

    public String degradeFallback(Throwable t) {
        System.err.println("----> 触发 异常时的降级策略:" + t);
        return "执行 异常降级方法";
    }
}
