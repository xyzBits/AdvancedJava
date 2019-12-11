package com.dongfang.advanced.net.protocol.tcp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.channels.UnresolvedAddressException;

/**
 * 模拟登录：单向
 */
public class LoginClient {
    public static void main(String[] args) throws IOException {
        System.out.println("----------------------LOGIN_CLIENT---------------------------");
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("请输入密码");
        String uname = console.readLine();
        System.out.println("请输入用户名");
        String password = console.readLine();

        //  1、指定服务器的地址和端口，使用Socket创建客户端
        Socket client = new Socket("localhost", 31945);

        //  2、操作：输入输出流操作
        DataOutputStream dos = new DataOutputStream(client.getOutputStream());
        dos.writeUTF("uname=" + uname + "&password=" + password);
        dos.flush();

        BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        String msg = reader.readLine();
        System.out.println("msg = " + msg);
        reader.close();

        //  3、释放资源
        dos.close();
        client.close();
    }
}
