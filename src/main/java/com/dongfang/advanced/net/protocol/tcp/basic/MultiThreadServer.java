package com.dongfang.advanced.net.protocol.tcp.basic;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadServer {
    public static void main(String[] args) throws IOException {

        System.out.println("---------------SERVER---------------------");
        ServerSocket server = new ServerSocket(31945);
        boolean isRunning = true;

        while (isRunning) {
            Socket client = server.accept();
            System.out.println("一个客户端建立了连接");
            new Thread(new Channel(client)).start();
        }

        server.close();
    }

    private static class Channel implements Runnable {
        private Socket client;
        private DataInputStream dis;
        private DataOutputStream dos;

        public Channel(Socket client) {
            this.client = client;
            try {
                this.dis = new DataInputStream(client.getInputStream());
                this.dos = new DataOutputStream(client.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void run() {
            String[] dataArr = receive().split("&");

            for (String str : dataArr) {
                String[] userInfo = str.split("=");
                System.out.println(userInfo[0] + "---->" + userInfo[1]);
            }
            send("登录成功");

            release();
        }


        private String receive() {
            try {
                return dis.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }
        }

        private void send(String msg) {
            try {
                dos.writeUTF(msg);
                dos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void release() {
            AutoCloseable[] autoCloseables = {dis, dos, client};
            for (AutoCloseable autoCloseable : autoCloseables) {
                try {
                    autoCloseable.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
