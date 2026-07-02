# SpringCloud 学习项目

## Nacos

### 注意事项
yaml中的 namespace 对应的配置是 **Nacos 的命名空间 ID**
```yaml
spring:
  application:
    name: yefeng-order
  cloud:
    nacos:
      discovery:
        server-addr: ${spring.cloud.nacos.server-addr}
        namespace: 20220411 # 这里的namespace 对应 nacos 命名空间的id
```