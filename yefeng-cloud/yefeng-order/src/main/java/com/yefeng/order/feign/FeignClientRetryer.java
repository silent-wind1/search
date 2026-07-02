package com.yefeng.order.feign;

import feign.RetryableException;
import feign.Retryer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author wind
 * @description:
 * @date 2026/7/2 20:15
 */
@Slf4j
@Component
public class FeignClientRetryer implements Retryer {
    private int start = 1;
    private int end = 3;

    @Override
    public void continueOrPropagate(RetryableException e) {
        // 是否需要进行重试取决于该方法是否抛出异常，如果抛出异常重试结束
        if (start >= end) {
            log.error("重试次数：{}", start);
            throw new RuntimeException(e);
        }
        start++;
    }

    @Override
    public Retryer clone() {
        return new FeignClientRetryer();
    }
}
