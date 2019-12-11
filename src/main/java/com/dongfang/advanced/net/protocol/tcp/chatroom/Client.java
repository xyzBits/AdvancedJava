package com.dongfang.advanced.net.protocol.tcp.chatroom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * 实现一个客户可以正常收发信息
 */
public class Client {
    public static void main(String[] args) throws IOException {
        System.out.println("---------------CLIENT------------------");
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("请输入你的用户名：");
        String userName = console.readLine();

        Socket client = new Socket("localhost", 31945);

        new Thread(new Sender(client, userName)).start();
        new Thread(new Receiver(client)).start();

    }
}
