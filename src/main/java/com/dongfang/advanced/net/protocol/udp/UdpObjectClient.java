package com.dongfang.advanced.net.protocol.udp;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.Date;

/**
 * 引用数据类型 发送端
 * 1、使用DatagramSocket 指定端口 创建发送端
 * 2、准备基本数据类型，将基本类型 转成字节数组
 * 3、将数据封装成DatagramPacket包裹，需要指定目的地
 * 4、发送包裹 send(DatagramPacket p)
 * 5、释放资源
 */
public class UdpObjectClient {
    public static void main(String[] args) throws IOException {
        System.out.println("引用类型发送方启动中");

        // * 1、使用DatagramSocket 指定端口 创建发送端
        DatagramSocket client = new DatagramSocket(8888);

        // * 2、准备数据 转成字节数组
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(baos));
        // 操作数据类型+数据
        oos.writeUTF("我是西方失败");
        oos.writeObject(new Date());

        byte[] datas = baos.toByteArray();
        oos.close();


        // * 3、将数据封装成DatagramPacket包裹，需要指定目的地
        DatagramPacket packet;
        packet = new DatagramPacket(datas, 0, datas.length,
                new InetSocketAddress("localhost", 9999));

        // * 4、发送包裹 send(DatagramPacket p)
        client.send(packet);

        // * 5、释放资源
        client.close();
    }
}


