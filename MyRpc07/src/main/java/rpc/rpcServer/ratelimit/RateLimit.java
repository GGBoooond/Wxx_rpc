package rpc.rpcServer.ratelimit;

/**
 * @author wxx
 * @version 1.0
 * @create 2024/3/31 17:43
 */
public interface RateLimit {
    //获取访问许可
    boolean getToken();
}
