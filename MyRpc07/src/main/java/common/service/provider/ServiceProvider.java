package common.service.provider;



import rpc.rpcServer.ratelimit.provider.RateLimitProvider;
import serviceRegister.ServiceRegister;
import serviceRegister.ZKServiceRegisterAndDiscoverer;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wxx
 * @version 1.0
 * @create 2024/2/16 17:35
 */
public class ServiceProvider {
    private Map<String,Object> interfaceProvider;
    private int port;
    private String host;
    private ServiceRegister serviceRegister;

    private RateLimitProvider rateLimitProvider;

    public ServiceProvider(String host,int port) throws InterruptedException {
        //需要传入服务端自身的网络地址
        this.host=host;
        this.port=port;
        this.interfaceProvider=new HashMap<>();
        this.serviceRegister=new ZKServiceRegisterAndDiscoverer();
        this.rateLimitProvider=new RateLimitProvider();
    }

    public void provideServiceInterface(Object service){
        String serviceName=service.getClass().getName();
        Class<?>[] interfaceName=service.getClass().getInterfaces();

        for (Class<?> clazz:interfaceName){
            //本机的映射表
            interfaceProvider.put(clazz.getName(),service);
            //在注册中心注册服务
            serviceRegister.register(clazz.getName(),new InetSocketAddress(host,port));
        }
    }

    public Object getService(String interfaceName){
        return interfaceProvider.get(interfaceName);
    }
    public RateLimitProvider getRateLimitProvider(){
        return rateLimitProvider;
    }
}
