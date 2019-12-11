package com.dongfang.advanced.net.protocol.tcp;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.channels.SocketChannel;

/**
 * 熟悉TCP编程的流程
 * 创建客户端
 *      1、指定服务器的地址和端口，使用Socket创建客户端
 *      2、操作：输入输出流操作
 *      3、释放资源
 */
public class SimpleTcpClient {
    public static void main(String[] args) throws IOException {
        System.out.println("----------------------CLIENT---------------------------");

        //  1、指定服务器的地址和端口，使用Socket创建客户端
        Socket client = new Socket("localhost", 31945);

        //  2、操作：输入输出流操作
        DataOutputStream dos = new DataOutputStream(client.getOutputStream());
        String data = "hello";
        dos.writeUTF(data);
        dos.flush();

        //  3、释放资源
        dos.close();
        client.close();
    }
}
