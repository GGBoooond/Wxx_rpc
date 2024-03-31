package rpc.rpcServer.ratelimit.provider;

import rpc.rpcServer.ratelimit.RateLimit;
import rpc.rpcServer.ratelimit.impl.TokenBucketRateLimitImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wxx
 * @version 1.0
 * @create 2024/3/31 19:37
 */
public class RateLimitProvider {
    private Map<String, RateLimit> rateLimitMap=new HashMap<>();

    public RateLimit getRateLimit(String interfaceName){
        if(!rateLimitMap.containsKey(interfaceName)){
            RateLimit rateLimit=new TokenBucketRateLimitImpl(100,10);
            rateLimitMap.put(interfaceName,rateLimit);
            return rateLimit;
        }
        return rateLimitMap.get(interfaceName);
    }
}
