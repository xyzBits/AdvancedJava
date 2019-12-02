package com.dongfang.advanced.net.protocol.udp.chat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * 接收端：使用面向对象封装
 */
public class ChatReceiver implements Runnable {
    private DatagramSocket server;
    private String from;

    public ChatReceiver(int port, String from) {
        this.from = from;
        try {
            server = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        while (true) {
            try {
                byte[] container = new byte[1024 * 60];
                DatagramPacket packet = new DatagramPacket(container, 0, container.length);
                server.receive(packet);
                byte[] datas = packet.getData();
                int len = packet.getLength();
                String data = new String(datas, 0, len);
                System.out.println(from + "说： " + data);

                if ("bye".equalsIgnoreCase(data)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        server.close();
    }
}
