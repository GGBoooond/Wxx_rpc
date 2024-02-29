package com.server.impl;

import com.server.RpcServer;
import com.server.work.WorkThread;
import com.service.provider.ServiceProvider;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

/**
 * @author wxx
 * @version 1.0
 * @create 2024/2/12 11:37
 */
@AllArgsConstructor
public class SimpleRPCRPCServer implements RpcServer {
    private ServiceProvider serviceProvide;
    @Override
    public void start(int port) {
        try {
            ServerSocket serverSocket=new ServerSocket(port);
            System.out.println("服务器启动了");
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new WorkThread(socket,serviceProvide)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {

    }
}
