package com.dongfang.advanced.net.app.chatroom;

import com.dongfang.advanced.util.CloseUtils;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class Receiver implements Runnable {
    private Socket client;
    private DataInputStream dis;
    private boolean isRunning;

    public Receiver(Socket client) {
        this.client = client;
        isRunning = true;
        try {
            dis = new DataInputStream(client.getInputStream());
        } catch (IOException e) {
            System.out.println("get client input stream with error " + e);
            release();
        }
    }

    @Override
    public void run() {
        while (isRunning) {
            String msg = receive();
            if (!msg.equals("")) {
                System.out.println(msg);
            }
        }
    }

    private String receive() {
        String msg = "";
        try {
            msg = dis.readUTF();
        } catch (IOException e) {
            System.out.println("receive msg with error, error is " + e);
            release();
        }
        return msg;
    }

    private void release() {
        isRunning = false;
        CloseUtils.close(dis, client);
    }
}
