package com.dongfang.advanced.net.app.chatroom;

import com.dongfang.advanced.util.CloseUtils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Sender implements Runnable {
    private Socket client;
    private BufferedReader console;
    private DataOutputStream dos;
    private boolean isRunning;
    private String userName;

    public Sender(Socket client, String userName) {
        this.client = client;
        this.userName = userName;
        this.console = new BufferedReader(new InputStreamReader(System.in));
        isRunning = true;
        try {
            this.dos = new DataOutputStream(client.getOutputStream());
            // 构造好后发送用户名
            send(userName);
        } catch (IOException e) {
            System.out.println("get output stream of client with error, error is " + e);
            release();
        }

    }

    @Override
    public void run() {
        while (isRunning) {
            String msg = readMsgFromConsole();
            if (!msg.equals("")) {
                send(msg);
            }
        }
    }

    private String readMsgFromConsole() {
        String msg = "";
        try {
            msg = console.readLine();
        } catch (IOException e) {
            System.out.println("read input from console with error, error is " + e);
        }
        return msg;
    }

    private void send(String msg) {
        try {
            dos.writeUTF(msg);
            dos.flush();
        } catch (IOException e) {
            System.out.println("send msg with error, error is " + e);
            release();
        }
    }

    private void release() {
        isRunning = false;
        CloseUtils.close(console, dos, client);
    }
}
