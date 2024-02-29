package com.server;

import com.server.impl.NettyRPCRPCServer;
import com.service.BlogService;
import com.service.Impl.BlogServiceImpl;
import com.service.Impl.UserServiceImpl;
import com.service.UserService;
import com.service.provider.ServiceProvider;

/**
 * @author wxx
 * @version 1.0
 * @create 2024/2/11 19:39
 */
public class TestServer {
    public static void main(String[] args) {
        UserService userService=new UserServiceImpl();
        BlogService blogService=new BlogServiceImpl();
        //Map<String,Object> serviceProvide=new HashMap<>();
        //serviceProvide.put("com.service.UserService",userService);
        //serviceProvide.put("com.service.BlogService",blogService);
        //RpcServer rpcServer=new SimpleRPCRPCServer(9999);
        //重构
        ServiceProvider serviceProvider=new ServiceProvider("127.0.0.1",9999);
        serviceProvider.provideServiceInterface(userService);
        serviceProvider.provideServiceInterface(blogService);

        RpcServer rpcServer=new NettyRPCRPCServer(serviceProvider);
        rpcServer.start(9999);
    }
}
