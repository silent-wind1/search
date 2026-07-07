package com.yefeng.order.config;

import com.yefeng.order.mapper.OrderMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Redisson 布隆过滤器初始化器
 * <p>
 * 在应用启动时从数据库加载已有订单 ID，同步到 Redisson 布隆过滤器中。
 * 由于 RBloomFilter 数据存储在 Redis 中，多次重启不会丢失已有数据，
 * add() 方法对已存在的元素返回 false，仅对新元素执行实际写入。
 * </p>
 *
 * @author yefeng
 */
@Slf4j
@Component
public class RedisBloomFilterInitializer {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private RBloomFilter<String> orderIdBloomFilter;

    /**
     * 启动时从数据库加载订单 ID 并同步到布隆过滤器
     */
    @PostConstruct
    public void init() {
        long beforeCount = orderIdBloomFilter.count();

        List<Long> orderIds = orderMapper.findAllOrderIds();
        int newCount = 0;
        for (Long orderId : orderIds) {
            if (orderIdBloomFilter.add(String.valueOf(orderId))) {
                newCount++;
            }
        }

        long afterCount = orderIdBloomFilter.count();
        log.info("Redisson 布隆过滤器 [order:bloom] 同步完成: "
                        + "已有 {} 个, DB 共 {} 个, 新增 {} 个, 当前共 {} 个",
                beforeCount, orderIds.size(), newCount, afterCount);
    }

}
