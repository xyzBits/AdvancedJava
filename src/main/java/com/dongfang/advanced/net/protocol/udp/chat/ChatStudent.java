package com.dongfang.advanced.net.protocol.udp.chat;

/**
 * 加入多线程，实现双向交流，模拟在线咨询
 */
public class ChatStudent {
    public static void main(String[] args) {
        new Thread(new ChatSender(7777, "localhost", 9999)).start(); // 发送

        new Thread(new ChatReceiver(8888, "老师")).start(); // 接收

    }
}
