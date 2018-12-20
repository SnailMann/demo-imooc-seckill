# Spring Boot Seckill Demo


### 通用返回对象

- Result : 通用返回对象
- CodeMsg : 通用返回信息


### 两次MD5

1. 用户端：Password = MD5(明文 + 固定Salt)   => 防止用户密码明文方式在网络上传输
2. 服务端：Password = MD5(用户输入 + 随机Salt)



### 自定义JSR303参数校验(注解，实现类)

- IsMobile注解
- IsMobileValidator实现类


### 自定义全局异常处理

- GloabalExecptionHandler
- GlobalException

### 分布式Session方案


