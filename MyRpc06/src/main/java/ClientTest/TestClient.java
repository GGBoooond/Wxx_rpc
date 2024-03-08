package ClientTest;

import common.pojo.User;
import common.service.UserService;
import rpc.rpcClient.client.RpcClient;
import rpc.rpcClient.client.impl.NettyRpcClient;
import rpc.rpcClient.proxy.ClientProxy;

/**
 * @author wxx
 * @version 1.0
 * @create 2024/2/6 18:39
 */
public class TestClient {
    public static void main(String[] args) throws InterruptedException {
        RpcClient rpcClient = new NettyRpcClient();
        ClientProxy clientProxy = new ClientProxy(rpcClient);
        UserService proxy = clientProxy.getProxy(UserService.class);

        User user = proxy.getUserByUserId(1);
        System.out.println("从服务端得到的user=" + user.toString());

        User u = User.builder().id(100).userName("wxx").sex(true).build();
        Integer id = proxy.insertUserId(u);
        System.out.println("向服务端插入user的id" + id);
    }
}