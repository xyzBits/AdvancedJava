package com.dongfang.advanced.net.protocol.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * 发送端
 *
 * 使用基于UDP协议的socket网络编程实现
 * 不需要利用io流实现数据的传输
 * 每个数据发送单元被统一封装成数据包的方式，发送发将数据包发送到网络中，数据包在网络中去寻找目的地
 *
 * UDP基本概念
 *  DatagramSocket 用于发送或接收数据包的套接字
 *  DatagramPacket 数据包
 *
 * 基本流程
 * 1、使用DatagramSocket 指定端口 创建发送端
 * 2、准备数据 转成字节数组
 * 3、将数据封装成DatagramPacket包裹，需要指定目的地
 * 4、发送包裹 send(DatagramPacket p)
 * 5、释放资源
 */
public class UdpClient {
    public static void main(String[] args) throws IOException {
        System.out.println("发送方启动中");

        // * 1、使用DatagramSocket 指定端口 创建发送端
        DatagramSocket client = new DatagramSocket(8888);

        // * 2、准备数据 转成字节数组
        String data = "hello xifang shibai";
        byte[] datas = data.getBytes();

        // * 3、将数据封装成DatagramPacket包裹，需要指定目的地
        DatagramPacket packet;
        // 构造数据报包，用来将长度为 length 的包发送到指定主机上
的指定端口
        packet = new DatagramPacket(datas, datas.length,
                new InetSocketAddress("localhost", 9999));

        // * 4、发送包裹 send(DatagramPacket p)
        client.send(packet);

        // * 5、释放资源
        client.close();
    }
}


