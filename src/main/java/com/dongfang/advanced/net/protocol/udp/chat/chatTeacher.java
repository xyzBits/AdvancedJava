package com.dongfang.advanced.net.protocol.udp.chat;

public class chatTeacher {
    public static void main(String[] args) {
        new Thread(new ChatReceiver(9999, "学生")).start(); // 接收

        new Thread(new ChatSender(5555, "localhost", 8888)).start(); // 发送

    }
}
