package com.dongfang.advanced.net.protocol.udp.chat;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

/**
 * 多次交流 发送端
 * 1、使用DatagramSocket 指定端口 创建发送端
 * 2、准备基本数据类型，将基本类型 转成字节数组
 * 3、将数据封装成DatagramPacket包裹，需要指定目的地
 * 4、发送包裹 send(DatagramPacket p)
 * 5、释放资源
 */
public class UdpChatClient {
    public static void main(String[] args) throws IOException {
        System.out.println("聊天发送方启动中");

        // * 1、使用DatagramSocket 指定端口 创建发送端
        DatagramSocket client = new DatagramSocket(8888);

        // * 2、准备数据 转成字节数组
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String data = reader.readLine();
            byte[] datas = data.getBytes();
            // * 3、将数据封装成DatagramPacket包裹，需要指定目的地
            DatagramPacket packet;
            packet = new DatagramPacket(datas, datas.length,
                    new InetSocketAddress("localhost", 9999));

            // * 4、发送包裹 send(DatagramPacket p)
            client.send(packet);

            if ("bye".equalsIgnoreCase(data)) break;
        }

        // * 5、释放资源
        client.close();
    }
}


