package com;

import com.Message.RpcRequest;
import com.Message.RpcResponse;
import com.service.Impl.UserServiceImpl;
import com.service.UserService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * @author wxx
 * @version 1.0
 * @create 2024/2/1 21:46
 */
public class RpcServer {
    public static void main(String[] args) {
        UserService userService=new UserServiceImpl();
        try {
            ServerSocket serverSocket=new ServerSocket(9999);
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(()->{
                    try {
                        ObjectOutputStream oos=new ObjectOutputStream(socket.getOutputStream());
                        ObjectInputStream ois=new ObjectInputStream(socket.getInputStream());
                        //读取客户端传过来的request
                        RpcRequest request = (RpcRequest) ois.readObject();
                        //反射调用对应方法
                        Method method=userService.getClass()
                                .getMethod(request.getMethodName(),request.getParamsType());
                        Object invoke=method.invoke(userService,request.getParams());
                        //封装，写入response对象
                        oos.writeObject(RpcResponse.sussess(invoke));
                        oos.flush();
                    } catch (IOException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException |
                             InvocationTargetException e) {
                        e.printStackTrace();;
                        System.out.println("IO流获取数据错误");
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("服务器启动失败");
        }
    }
}
