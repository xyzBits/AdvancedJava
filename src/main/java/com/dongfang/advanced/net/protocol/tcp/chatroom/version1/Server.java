package com.dongfang.advanced.net.protocol.tcp.chatroom.version1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 收发多条消息
 */
public class Server {
    public static void main(String[] args) throws IOException {
        System.out.println("------------------SERVER-------------");
        ServerSocket server = new ServerSocket(31945);
        while (true) {
            Socket client = server.accept();
            System.out.println("一个客户端建立连接");

            new Thread(() -> {
                try {
                    DataInputStream dis = new DataInputStream(client.getInputStream());
                    DataOutputStream dos = new DataOutputStream(client.getOutputStream());
                    boolean isRunning = true;
                    while (isRunning) {
                        String receivedMsg = dis.readUTF();
                        System.out.println("receivedMsg = " + receivedMsg);

                        String sentMsg = "fanhui " + receivedMsg;
                        dos.writeUTF(sentMsg);
                        dos.flush();
                    }

                    dos.close();
                    dis.close();
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }

    }
}
