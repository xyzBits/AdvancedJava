package com.dongfang.advanced.net.location;

import org.junit.jupiter.api.Test;

import java.net.InetSocketAddress;

/**
 * 端口：
 *      1、区分软件
 *      2、2个字节 0-65535 udp tcp是独立的，同一个协议下端口不能冲突
 *      3、不同的协议有不同的端口
 *      4、使用尽可能大的端口
 *  同一个协议中，端口不要冲突，不同的协议，端口可以冲突
 *
 *  InetSocketAddress是SocketAddress的实现类
 *      构造器
 *          (地址、域名，端口)
 *      方法
 *          getAddress()
 *          getPort()
 *          getHostName()
 *
 *  公认端口 0-1023 比如80端口分配在www, 21分配给ftp
 *  注册端口 1024-49151 分配给用户进程或者应用程序
 *  动态私有端口  49152-65535
 *
 *  查看所有端口 netstat -ano
 *  查看指定端口 netstat -aon | findstr "8080"
 *  查看端口对应的指定进程 tasklist | findstr "8080"
 *
 */
public class PortDemo {
    @Test
    public void testInetSocketAddress() {
        // 包含端口
        InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 8080);
        InetSocketAddress socketAddress1 = new InetSocketAddress("localhost", 9000);
        System.out.println("socketAddress.getHostName() = " + socketAddress.getHostName());
        System.out.println("socketAddress1.getAddress() = " + socketAddress1.getAddress());

        System.out.println("socketAddress.getPort() = " + socketAddress.getPort());
    }
}
