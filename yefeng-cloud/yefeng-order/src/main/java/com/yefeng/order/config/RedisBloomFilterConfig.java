package com.yefeng.order.config;

import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Redisson 布隆过滤器配置
 * <p>
 * 使用 Redisson 的 {@link RBloomFilter} 实现分布式布隆过滤器，
 * 数据存储在 Redis 中，多个服务实例共享，重启不丢失。
 * </p>
 * <p>
 * 预期 10,000 个订单，误判率 1%，Redisson 自动计算最优位图参数。
 * </p>
 *
 * @author yefeng
 */
@Configuration
public class RedisBloomFilterConfig {

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 订单 ID 布隆过滤器 (Redisson RBloomFilter 实现)
     * <p>
     * tryInit 是幂等的：首次调用写入配置到 Redis，后续调用返回 false。
     * 如需重建过滤器，需要先调用 {@link RBloomFilter#delete()} 删除旧数据。
     * </p>
     */
    @Bean
    public RBloomFilter<String> orderIdBloomFilter() {
        RBloomFilter<String> filter = redissonClient.getBloomFilter(
                "order:bloom",
                StringCodec.INSTANCE
        );
        // 幂等初始化：预期 10000 元素，1% 误判率
        filter.tryInit(10_000L, 0.01);
        return filter;
    }

}
