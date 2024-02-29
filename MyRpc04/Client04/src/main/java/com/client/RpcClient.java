package com.client;

import com.Message.RpcRequest;
import com.Message.RpcResponse;

/**
 * @author wxx
 * @version 1.0
 * @create 2024/2/20 15:32
 */
public interface RpcClient {
    RpcResponse sendRequest(RpcRequest request);
}
