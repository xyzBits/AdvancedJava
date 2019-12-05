package com.dongfang.advanced.net.protocol.tcp.basic;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 模拟登录；单向
 */
public class LoginServer {
    public static void main(String[] args) throws IOException {
        System.out.println("----------------------LOGIN_SERVER---------------------------");

        // 1、指定商品，使用ServerSocket创建服务器
        ServerSocket server = new ServerSocket(31945);

        // 2、阻塞式等待连接 accept
        Socket client = server.accept();
        // http://localhost:31945就会建立连接
        System.out.println("一个客户端建立了连接");

        // 3、操作：输入输出流操作
        DataInputStream dis = new DataInputStream(client.getInputStream());
        String datas = dis.readUTF();
        System.out.println("datas = " + datas);
        String[] dataArr = datas.split("&");

        for (String str : dataArr) {
            String[] userInfo = str.split("=");
            System.out.println(userInfo[0] + "---->" + userInfo[1]);
        }

        DataOutputStream dos = new DataOutputStream(client.getOutputStream());
        dos.writeUTF("登录成功");
        dos.close();

        // 4、释放资源
        dis.close();
        client.close();
    }
}
