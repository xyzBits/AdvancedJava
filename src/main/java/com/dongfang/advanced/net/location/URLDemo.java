package com.dongfang.advanced.net.location;

import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

/**
 *  URI
 *      Uniform Resource Indentifier 统一资源标志符，用来标识抽象或物理资源的一个紧凑字符串
 *  URL
 *      Uniform Resource Location 统一资源定位符，一种定位资源的主要访问机制中的字符串，一个标准URL必须包括
 *      protocol host port path parameter anchor
 *  URN
 *      Uniform Resource Name 统一资源名称，资源的名称
 *  http://www.google.com:80/index.html?name=java
 *      协议
 *      存放资源的主机域名
 *      端口号
 *      资源文件名
 *      参数
 *      锚点 同一个网站或者同一个页面的跳转
 *
 *   互联网的三大基石：html http url
 *
 *
 *   统一资源定位器，区分资源，定位资源
 *
 *   web spider
 *      url
 *      下载
 *      分析
 */
public class URLDemo {
    @Test
    public void testUrl() throws MalformedURLException {
        URL url = new URL("http://www.google.com:80/index.html?name=java&age=18#a");
        // 获取四个值
        System.out.println("url.getProtocol() = " + url.getProtocol());

        System.out.println("url.getHost() = " + url.getHost());

        System.out.println("url.getFile() = " + url.getFile()); //不包含锚点

        System.out.println("url.getPath() = " + url.getPath());

        System.out.println("url.getPort() = " + url.getPort());

        // 参数
        System.out.println("url.getQuery() = " + url.getQuery());

        // 锚点
        System.out.println(url.getRef());
    }
}
