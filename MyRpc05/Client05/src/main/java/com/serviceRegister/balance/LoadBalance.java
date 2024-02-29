package com.serviceRegister.balance;

import java.util.List;

/**
 * @author wxx
 * @version 1.0
 * @create 2024/2/28 15:48
 * 给服务地址列表，根据不同的负载均衡策略选择一个
 */
public interface LoadBalance {
    String balance(List<String> addressList);
}
