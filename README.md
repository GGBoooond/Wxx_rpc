从第1版到第6版，逐步更新，优化框架的性能
会持续进行框架的升级...

# RPC概念

### 概念

1. RPC（Remote Procedure Call Protocol） 远程过程调用协议。
2. RPC是一种通过网络从远程计算机程序上请求服务，不需要了解底层网络技术的协议。
3. RPC主要作用就是不同的服务间方法调用就像本地调用一样便捷。



### 常用RPC技术或框架

应用级的服务框架：阿里的 Dubbo/Dubbox、Google gRPC、Spring Boot/Spring Cloud。
远程通信协议：RMI、Socket、SOAP(HTTP XML)、REST(HTTP JSON)。
通信框架：MINA 和 Netty



### 为什么要有RPC？

1. 服务化：微服务化，跨平台的服务之间远程调用；
2. 分布式系统架构：分布式服务跨机器进行远程调用；
3. 服务可重用：开发一个公共能力服务，供多个服务远程调用。
4. 系统间交互调用：两台服务器A、B，服务器A上的应用a需要调用服务器B上的应用b提供的方法，而应用a和应用b不在一个内存空间，不能直接调用，此时，需要通过网络传输来表达需要调用的语义及传输调用的数据。
   

#### 使用场景

1. `大型网站`：内部涉及多个子系统，服务、接口较多。
2. `注册发现机制`：如Nacos、Dubbo等，一般都有注册中心，服务有多个实例，调用方调用的哪个实例无感知。
3. `安全性`：不暴露资源
4. `服务化治理`：微服务架构、分布式架构。



### 架构图

![在这里插入图片描述](README.assets/655c04a02b08474e985ff4bf8a561d12.png)



### 核心功能概念

核心功能实现主要分为**服务寻址**、**序列化和反序列化**、**网络传输功能**。

#### 服务寻址功能

**Call ID映射：**

​	本地：在本地方法调用中，函数体是直接通过函数指针来指定的，但是在远程调用中，由于两个进程的地址空间完全不一样，函数指针不起作用。
​	远程：RPC中所有函数或方法都有自己的一个ID，在所有进程中都唯一。客户端在做远程过程调用时，必须附上这个ID，即客户端会查一下表，找出相应的Call ID，然后传给服务端，服务端也会查表，来确定客户端需要调用的函数，然后执行相应函数的代码。
​	Call ID映射表一般是一个哈希表。



#### 序列化和反序列化功能

**概述：**

- 序列化：将消息对象转换为二进制流。

- 反序列化：将二进制流转换为消息对象。



**必要性**：
远程调用涉及到数据的传输，在本地调用中，只需要将数据压入栈中，然后让函数去栈中读取即可。
但远程的数据传输，由于客户端和服务端不在同一个服务器上，涉及不同的进程，不能通过内存传递参数，此时就需要将客户端先将请求参数转成字节流（编码），传递给服务端，服务端再将字节流转为自己可读取格式（解码），这就是序列化和反序列化的过程。反之，服务端返回值也逆向经历序列化和反序列化到客户端。

**序列化的优势：**
将消息对象转为二进制字节流，便于网络传输。
可跨平台、跨语言。如Python编写的客户端请求序列化参数传输到Java编写的服务端进行反序列化。

#### 网络传输功能

**作用**：

- 客户端将Call ID和序列化后的参数字节流传输给服务端。
- 服务端将序列化后的调用结果回传给客户端。

**协议**：
  主要有TCP、UDP、HTTP协议。

**基于TCP协议**

​	客户端和服务端建立Socket连接。
​	客户端通过Socket将需要调用的接口名称、方法名称及参数序列化后传递给服务端。
服务端反序列化后再利用反射调用对应的方法，将结果返回给客户端。

**基于HTTP协议**

​	客户端向服务端发送请求，如GET、POST、PUT、DELETE等请求。
​	服务端根据不同的请求参数和请求URL进行方法调用，返回JSON或者XML数据结果。

**TCP和HTTP对比**

- 基于TCP协议实现的RPC调用，由于是底层协议栈，更佳灵活的对协议字段进行定制，可减少网络开销，提高性能，实现更大的吞吐量和并发数。但**，底层复杂，实现代价高**。

- 基于HTTP协议实现的RPC调用，已封装实现序列化，但HTTP属于应用层协议，HTTP传输**所占用的字节数比TCP更高，传输效率对比TCP较低**。
  



# MyRPC01-


1.定义了通用消息对象 RpcRequest  RpcResponse
2.客户端使用动态代理，底层封装统一的RpcRequset消息，向客户端隐藏底层的消息传输

![](https://cdn.nlark.com/yuque/0/2024/jpeg/42457196/1709192270852-dc6effb6-ec78-47b5-9468-799ff00848f3.jpeg)
#### 存在的痛点

1. 服务端我们直接绑定的是UserService服务，如果还有其它服务接口暴露呢?（多个服务的注册）
2. 服务端以BIO的方式性能是否太低，
3. 服务端功能太复杂：监听，处理。需要松耦合


...

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



使用负载均衡分配流量




客户端实现**负载均衡**多种策略：轮询，随机，LRU最近最少使用

![](https://cdn.nlark.com/yuque/0/2024/jpeg/42457196/1709197012024-34f4cf7e-0af6-46bc-b415-2e7133eabfbe.jpeg)



# MyRPC06 -

实现 客户端的本地缓存

使用zookeeper的Watcher监听机制，通过监听节点变化 ，动态刷新本地缓存



![1709967476143](README.assets/1709967476143.png)



# MyRPC07-（进行中）

预计实现超时重试 ，服务降级  ，配置中心白名单，服务节点下线，

**（已经实现）**

1.使用Google的Guava-Retry框架进行**超时重试**的实现：****

​	框架十分强大，可以根据返回值判断是否重试；可以设置重传策略和重传次数




**（待实现）**

2.在客户端进行超时重试时，不是什么服务都可以重试，只有**保证幂等性的服务可以重试**

可以将zookeeper设为配置中心，服务端将幂等性的服务加载到配置中心白名单上

客户端进行重试前需要判断该服务是否可以重试



**（已经实现）**

3.服务端实现 **服务降级 （故障降级和限流降级） 和服务下线**

**故障降级：**

​	当服务端 节点发生故障时，故障降级，快速返回fail数据；当故障次数到达一定频率后，在注册中心下线该节点



**限流降级：**

​	使用令牌桶算法，在服务端获取request中的接口名后，进行限流管理，判断是否能获取到令牌

​	如果获取不到，快速返回错误数据

​	（这里后续可以配合**负载均衡**，根据返回不同的信息，来动态调整负载因子，调整流量分布）





# 后续框架优化的思考





**1.服务熔断  的实现**

初步想法是在动态代理层设置熔断器， 监听or扫描配置中心中的下线节点名单，并进行熔断处理



**2..对于已经下线的服务节点 ，怎么处理**

将其放到配置中心的下线节点名单中。

一方面，客户端可以通过观察配置中心 的已下线服务节点，来判断是否需要调用某服务

另一方面，服务端可以对配置中心 的下线节点名单  ，开启定时任务  去探测节点是否存活





**3.如何让整个框架更利于拓展？**
如使用Java的SPI机制，配置化等等
:::


