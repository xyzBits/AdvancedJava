package com.dongfang.advanced.net.location;

import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * ip定位一个节点，路由，计算机
 * InetAddress
 * 1、getLocalHost
 * 2、getByName 根据域名，ip地址，调用dns服务
 *
 * 两个成员方法
 *  1、getHostAddress 返回主机的地址
 *  2、getHostName    返回主机的计算机名
 *
 *
 */
public class IPDemo {
    @Test
    public void getLocalInetAddress() throws UnknownHostException {
        // 使用getLocalHost方法创建InetAddress对象，表示的是本机
        InetAddress localHost = InetAddress.getLocalHost();
        System.out.println("localHost.getHostAddress() = " + localHost.getHostAddress());

        // 计算机名
        System.out.println("localHost.getHostName() = " + localHost.getHostName());
    }

    @Test
    public void getInetAddressByDomainName() throws UnknownHostException {

        // DNS将ip和字符串映射起来，人类更容易记忆字符串，dns由电信运营商实现
        // 根据域名得到InteAddress对象
        InetAddress address = InetAddress.getByName("www.google.com"); // 底层使用了dns的服务
        System.out.println("address.getHostAddress() = " + address.getHostAddress());
        System.out.println("address.getHostName() = " + address.getHostName());
    }

    // 69.171.228.74
    @Test
    public void getInteAddressByIp() throws UnknownHostException {
        InetAddress address = InetAddress.getByName("69.171.228.74");
        System.out.println("address.getHostName() = " + address.getHostName());
        System.out.println("address.getHostAddress() = " + address.getHostAddress());
    }
}
