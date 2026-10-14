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



### nacos redis配置
```yaml
spring:
  data:
    redis:
      host: localhost
      port: 6379
      password: '123456'
      database: 0
      timeout: 3000ms
```