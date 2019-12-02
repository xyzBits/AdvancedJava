package com.dongfang.advanced.net.protocol.udp.chat;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * 多次交流 接收端
 * 1、使用DatagramSocket 指定端口 创建接收端
 * 2、装备容器，封装成DatagramPacket包裹
 * 3、阻塞式接收包裹 receive(DatagramPacket p)
 * 4、分析数据  将字节数组还原为对应的类型即可
 * byte[] getData()
 * int    getLength()
 * 5、释放资源
 */
public class UdpChatServer {
    public static void main(String[] args) throws IOException {
        System.out.println("聊天接收方启动中");

        // * 1、使用DatagramSocket 指定端口 创建接收端
        DatagramSocket server = new DatagramSocket(9999);

        while (true) {
            // * 2、装备容器，封装成DatagramPacket包裹
            byte[] container = new byte[1024 * 60];
            DatagramPacket packet = new DatagramPacket(container, 0, container.length);

            // * 3、阻塞式接收包裹 receive(DatagramPacket p)
            server.receive(packet);

            // * 4、分析数据
            // *      byte[] getData()
            // *      int    getLength()

            byte[] datas = packet.getData();
            int len = packet.getLength();
            String data = new String(datas, 0, len);
            System.out.println(data);
            if ("bye".equalsIgnoreCase(data)) break;
        }
        // * 5、释放资源
        server.close();
    }


}