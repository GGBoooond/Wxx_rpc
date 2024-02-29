package com.server;

import com.server.impl.SimpleRPCRPCServer;
import com.service.BlogService;
import com.service.Impl.BlogServiceImpl;
import com.service.Impl.UserServiceImpl;
import com.service.UserService;
import com.service.provider.ServiceProvider;

import java.util.HashMap;
import java.util.Map;

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
        ServiceProvider serviceProvider=new ServiceProvider();
        serviceProvider.provideServiceInterface(userService);
        serviceProvider.provideServiceInterface(blogService);

        RpcServer rpcServer=new SimpleRPCRPCServer(serviceProvider);
        rpcServer.start(9999);
    }
}
