package serviceRegister;

import java.net.InetSocketAddress;

/**
 * @author wxx
 * @version 1.0
 * @create 2024/2/28 14:19
 */
// 服务注册接口，两大基本功能：
//  注册：保存服务与地址。
//  查询：根据服务名查找地址
public interface ServiceRegister {
    void register(String serviceName, InetSocketAddress serviceAddress);
    InetSocketAddress serviceDiscovery(String serviceName);
}
