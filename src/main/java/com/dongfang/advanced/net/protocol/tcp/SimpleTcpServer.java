package com.dongfang.advanced.net.protocol.tcp;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 熟悉TCP编程的流程
 * 创建服务器
 *      1、指定端口，使用ServerSocket创建服务器
 *      2、阻塞式等待连接 accept
 *      3、操作：输入输出流操作
 *      4、释放资源
 */
public class SimpleTcpServer {
    public static void main(String[] args) throws IOException {
        System.out.println("----------------------SERVER---------------------------");

        // 1、指定商品，使用ServerSocket创建服务器
        ServerSocket server = new ServerSocket(31945);

        // 2、阻塞式等待连接 accept
        Socket client = server.accept();
        // http://localhost:31945就会建立连接
        System.out.println("一个客户端建立了连接");

        // 3、操作：输入输出流操作
        DataInputStream dis = new DataInputStream(client.getInputStream());
        String data = dis.readUTF();
        System.out.println("data = " + data);

        // 4、释放资源
        dis.close();
        client.close();
    }
}
