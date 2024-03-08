package ServerTest;


import common.service.BlogService;
import common.service.Impl.BlogServiceImpl;
import common.service.Impl.UserServiceImpl;
import common.service.UserService;
import common.service.provider.ServiceProvider;
import rpc.rpcServer.server.RpcServer;
import rpc.rpcServer.server.impl.NettyRPCRPCServer;

/**
 * @author wxx
 * @version 1.0
 * @create 2024/2/11 19:39
 */
public class TestServer {
    public static void main(String[] args) throws InterruptedException {
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
