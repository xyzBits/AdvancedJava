package com.dongfang.advanced.net.protocol.udp.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

/**
 * 发送端：使用面向对象封装
 * 发送端要同时指定接收端的ip和port
 */
public class ChatSender implements Runnable {
    private DatagramSocket client;
    private BufferedReader reader;
    private String receiverIp;
    protected int receiverPort;

    public ChatSender(int port, String receiverIp, int receiverPort) {
        this.receiverIp = receiverIp;
        this.receiverPort = receiverPort;
        try {
            client = new DatagramSocket(port);
            reader = new BufferedReader(new InputStreamReader(System.in));
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            String data;
            try {
                data = reader.readLine();
                byte[] datas = data.getBytes();

                DatagramPacket packet = new DatagramPacket(datas, 0, datas.length,
                        new InetSocketAddress(this.receiverIp, this.receiverPort));
                client.send(packet);

                if ("bye".equalsIgnoreCase(data)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        client.close();

    }
}
