package com.dongfang.advanced.net.protocol.udp;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * 基本数据类型 接收端
 * 1、使用DatagramSocket 指定端口 创建接收端
 * 2、装备容器，封装成DatagramPacket包裹
 * 3、阻塞式接收包裹 receive(DatagramPacket p)
 * 4、分析数据  将字节数组还原为对应的类型即可
 *      byte[] getData()
 *      int    getLength()
 * 5、释放资源
 */
public class UdpTypeServer {
    public static void main(String[] args) throws IOException {
        System.out.println("基本类型接收方启动中");

        // * 1、使用DatagramSocket 指定端口 创建接收端
        DatagramSocket server = new DatagramSocket(9999);

        // * 2、装备容器，封装成DatagramPacket包裹
        byte[] container = new byte[1024 * 60];
        DatagramPacket packet = new DatagramPacket(container, container.length);

        // * 3、阻塞式接收包裹 receive(DatagramPacket p)
        server.receive(packet);

        // * 4、分析数据
        // *      byte[] getData()
        // *      int    getLength()

        byte[] datas = packet.getData();
        int len = packet.getLength();
        System.out.println(new String(datas, 0, len));
        DataInputStream dis = new DataInputStream(new BufferedInputStream(new ByteArrayInputStream(datas)));
        // 顺序与写出一致
        String msg = dis.readUTF();
        int age = dis.readInt();
        boolean flag = dis.readBoolean();
        char ch = dis.readChar();
        System.out.println("msg = " + msg);

        // * 5、释放资源
        server.close();
    }


}
