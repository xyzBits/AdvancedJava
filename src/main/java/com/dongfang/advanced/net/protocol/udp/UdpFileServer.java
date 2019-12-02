package com.dongfang.advanced.net.protocol.udp;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

/**
 * 文件数据类型 接收端
 * 1、使用DatagramSocket 指定端口 创建接收端
 * 2、装备容器，封装成DatagramPacket包裹
 * 3、阻塞式接收包裹 receive(DatagramPacket p)
 * 4、分析数据  将字节数组还原为对应的类型即可
 *      byte[] getData()
 *      int    getLength()
 * 5、释放资源
 */
public class UdpFileServer {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("文件类型接收方启动中");

        // * 1、使用DatagramSocket 指定端口 创建接收端
        DatagramSocket server = new DatagramSocket(9999);

        // * 2、装备容器，封装成DatagramPacket包裹
        byte[] container = new byte[1024 * 60];
        DatagramPacket packet = new DatagramPacket(container, 0, container.length);

        // * 3、阻塞式接收包裹 receive(DatagramPacket p)
        server.receive(packet);

        // * 4、分析数据
        // *      byte[] getData()
        // *      int    getLength()
        Path path = Paths.get("C:\\Users\\Li Dongfang\\Pictures\\myimage1.jpg");
        Files.write(path, container);

        // * 5、释放资源
        server.close();
    }


}