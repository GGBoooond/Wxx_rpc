
从第1版到第6版，逐步更新，优化框架的性能
会持续进行框架的升级...
# MyRPC01-


1.定义了通用消息对象 RpcRequest  RpcResponse
2.客户端使用动态代理，底层封装统一的RpcRequset消息，向客户端隐藏底层的消息传输

![](https://cdn.nlark.com/yuque/0/2024/jpeg/42457196/1709192270852-dc6effb6-ec78-47b5-9468-799ff00848f3.jpeg)
#### 存在的痛点

1. 服务端我们直接绑定的是UserService服务，如果还有其它服务接口暴露呢?（多个服务的注册）
2. 服务端以BIO的方式性能是否太低，
3. 服务端功能太复杂：监听，处理。需要松耦合


 

# MyRpc02-


建立本地服务暴露类，server将服务注册到serviceProvider中，rpcServer接收到消息，解析后在serviceProvider中寻找，调用

![](https://cdn.nlark.com/yuque/0/2024/jpeg/42457196/1709193193870-a6f7e0a1-38f9-4989-9244-0e518437957b.jpeg)
#### 此RPC最大的痛点

1. 传统的BIO与线程池网络传输性能低

如何在此基础上提升性能？

- 网络传输 从BIO -->NIO
- 序列化和反序列化方面提高效率
# MyRPC03-


1.重构客户端，抽象RpcClient接口
2.使用netty完成C-S之间的通信

![](https://cdn.nlark.com/yuque/0/2024/jpeg/42457196/1709194770098-4367eac0-f938-4457-ad90-47f4cfe1bd7c.jpeg)
#### 此RPC最大痛点

1. java自带序列化方式（Java序列化写入不仅是完整的类名，也包含整个类的定义，包含所有被引用的类），不够通用，不够高效

# MyRPC04-

1.自定义传输的消息格式，解决沾包问题
2.自定义编/解码器和序列化器

![](https://cdn.nlark.com/yuque/0/2024/jpeg/42457196/1709196150613-c0d4dd6b-b7f6-41e0-a6dc-779c7d3f5dc4.jpeg)
#### 此版本最大痛点

- 服务端与客户端通信的host与port预先就必须知道的，每一个客户端都必须知道对应服务的ip与端口号， 并且如果服务挂了或者换地址了，就很麻烦。扩展性也不强


# MyRPC05-

使用zookeeper作为注册中心
一个完整的RPC框架三个角色都有了：服务提供者，服务消费者，注册中心

![](https://cdn.nlark.com/yuque/0/2024/jpeg/42457196/1709196528949-e192f955-b935-450f-8435-526fd55dcb9f.jpeg)

- 根据服务名查询地址时，我们返回的总是第一个IP，导致这个提供者压力巨大，而其它提供者调用不到
# 


客户端实现负载均衡多种策略：轮询，随机，LRU最近最少使用

![](https://cdn.nlark.com/yuque/0/2024/jpeg/42457196/1709197012024-34f4cf7e-0af6-46bc-b415-2e7133eabfbe.jpeg)





更多功能待拓展中...

**1.容错机制 如果服务调用失败，消费者应该如何处理呢**

为保证分布式系统的高可用，我们通常会给服务的调用增加一定的容错机制，如失败重试，降级调用其它接口等
![image.png](https://cdn.nlark.com/yuque/0/2024/png/42457196/1709197169719-c587d17b-c221-4709-8ab5-6f8e72e05d2e.png#averageHue=%23fdfdfd&clientId=ua5674b0c-aad3-4&from=paste&height=399&id=uc5e34f82&originHeight=599&originWidth=974&originalType=binary&ratio=1.5&rotation=0&showTitle=false&size=27455&status=done&style=none&taskId=ud7950e98-d26b-4296-bddf-cb57ed32f6c&title=&width=649.3333333333334)

**2.服务提供者下线了怎么办？**  
需要一个失效节点剔除机制


**3.消费者每次都要从注册中心中拉取信息，性能会不会很差？**
可以用缓存来优化性能

**4.如何让整个框架更利于拓展？**
如使用Java的SPI机制，配置化等等
:::


