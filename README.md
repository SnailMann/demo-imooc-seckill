# Spring Boot Seckill Demo

### 两次MD5

1. 用户端：Password = MD5(明文 + 固定Salt)   => 防止用户密码明文方式在网络上传输
2. 服务端：Password = MD5(用户输入 + 随机Salt)