package com.dongfang.advanced.net.protocol.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * java.net.BindException: Address already in use: Cannot bind
 * 同一个协议下，商品不允许冲突
 *
 *
 *
 * 基本流程：接收端
 * 1、使用DatagramSocket 指定端口 创建接收端
 * 2、装备容器，封装成DatagramPacket包裹
 * 3、阻塞式接收包裹 receive(DatagramPacket p)
 * 4、分析数据
 *      byte[] getData()
 *      int    getLength()
 * 5、释放资源
 */
public class UdpServer {
    public static void main(String[] args) throws IOException {
        System.out.println("接收方启动中");

        // * 1、使用DatagramSocket 指定端口 创建接收端
        DatagramSocket server = new DatagramSocket(9999);

        // * 2、装备容器，封装成DatagramPacket包裹
        byte[] container = new byte[1024 * 60];
        // 构造 DatagramPacket，用来接收长度为 length 的数据包
        DatagramPacket packet = new DatagramPacket(container, container.length);

        // * 3、阻塞式接收包裹 receive(DatagramPacket p)
        server.receive(packet);

        // * 4、分析数据
        // *      byte[] getData()
        // *      int    getLength()

        byte[] datas = packet.getData();
        int len = packet.getLength();
        System.out.println(new String(datas, 0, len));

        // * 5、释放资源
        server.close();
    }


}


/**
 *
 TCP transfer control protocol
 一种面向连接（连接导向）的，可靠的，基于字节流的运输层（transport layer）通信协议

 特点：
 面向连接
 点到点通信
 高可靠性
 占用系统资源多，效率低

 UDP user datagram protocol
 一种无连接的传输层协议，提供面向事务的简单不可靠信息传送服务

 特点：
 非面向连接，传输不可靠，可能丢失
 发送主不管对方是否准备好，接收方收到也不确认
 可以广播发送
 非常简单的协议，开销小

 套接字socket
 我们开发的网络应用程序位于应用层，tcp udp属于传输层协议，在应用层如何使用传输层的服务呢，在应用层和传输层之间，则是使用套接字来进行分离的

 套接字就像是传输层为应用层开的一个小口，应用程序通过这个小口向远程发送数据，或者是接收远程发来的数据，而这个小口以内，也就是数据进入这个口之后，或者数据从这个口出来之前，是不知道，也不需要知道的，也不会关心它如何传输，这属于网络其他层次的工作



 基于tcp协议的socket编程 114查号台
 通信双方需要建立连接
 建立连接时双方存在主次之分

 基于udp协议的socket编程 qq聊天模式
 通信双方不需要建立连接
 通信双方完全平等

 */
