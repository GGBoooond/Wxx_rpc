package rpc.rpcClient.client;


import common.Message.RpcRequest;
import common.Message.RpcResponse;

/**
 * @author wxx
 * @version 1.0
 * @create 2024/2/20 15:32
 */
public interface RpcClient {
    RpcResponse sendRequest(RpcRequest request);
}
