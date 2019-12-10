package com.dongfang.advanced.net.protocol.tcp.chatroom.version1;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * 实现一个客户可以正常收发信息
 */
public class Client {
    public static void main(String[] args) throws IOException {
        System.out.println("---------------CLIENT------------------");
        Socket client = new Socket("localhost", 31945);

        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        DataOutputStream dos = new DataOutputStream(client.getOutputStream());
        DataInputStream dis = new DataInputStream(client.getInputStream());

        boolean isRunning = true;
        while (isRunning) {
            try {
                String sentMsg = console.readLine();

                dos.writeUTF(sentMsg);
                dos.flush();

                String receivedMsg = dis.readUTF();
                System.out.println("receivedMsg = " + receivedMsg);
            } catch (IOException e) {
                isRunning = false;
                e.printStackTrace();
            }
        }

        console.close();
        dos.close();
        dis.close();
        client.close();


    }
}
