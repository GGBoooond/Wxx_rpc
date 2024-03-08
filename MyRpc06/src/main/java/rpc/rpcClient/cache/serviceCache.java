package rpc.rpcClient.cache;

import org.apache.curator.framework.CuratorFramework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wxx
 * @version 1.0
 * @create 2024/3/8 16:04
 * 客户端 本地服务缓存
 */
public class serviceCache {
    //key: serviceName 服务名
    //value： addressList 服务提供者列表
    private static Map<String, List<String>> cache=new HashMap<>();
    //添加服务
    public void addServcieToCache(String serviceName,String address){
        if(cache.containsKey(serviceName)){
            List<String> addressList = cache.get(serviceName);
            addressList.add(address);
            System.out.println("将name为"+serviceName+"和地址为"+address+"的服务添加到本地缓存中");
        }else {
            List<String> addressList=new ArrayList<>();
            addressList.add(address);
            cache.put(serviceName,addressList);
        }
    }
    //从缓存中取服务地址
    public  List<String> getServcieFromCache(String serviceName){
        if(!cache.containsKey(serviceName)) {
            return null;
        }
        System.out.println("yyyyy");
        List<String> a=cache.get(serviceName);
        return a;
    }
    //从缓存中删除服务地址
    public void delete(String serviceName,String address){
        List<String> addressList = cache.get(serviceName);
        addressList.remove(address);
        System.out.println("将name为"+serviceName+"和地址为"+address+"的服务从本地缓存中删除");
    }
}
