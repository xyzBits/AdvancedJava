package com.dongfang.advanced.net.protocol.tcp.chatroom;

import com.dongfang.advanced.util.CloseUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 收发多条消息
 */
public class Server {
    private static CopyOnWriteArrayList<Channel> all = new CopyOnWriteArrayList<>();

    public static void main(String[] args) throws IOException {
        System.out.println("------------------SERVER-------------");
        ServerSocket server = new ServerSocket(31945);
        while (true) {
            Socket client = server.accept();
            System.out.println("一个客户端建立连接");
            Channel channel = new Channel(client); // 管理所有的成员
            all.add(channel);

            new Thread(channel).start();
        }
    }

    // 一个客户代表一个线程
    private static class Channel implements Runnable {
        private Socket client;
        private DataInputStream dis;
        private DataOutputStream dos;
        private boolean isRunning;
        private String userName;

        public Channel(Socket client) {
            this.client = client;
            try {
                dis = new DataInputStream(client.getInputStream());
                dos = new DataOutputStream(client.getOutputStream());
                isRunning = true;
                // 建立连接后，马上获取用户名
                this.userName = receive();
                this.send("欢迎您进入聊天室");
                sendOthers(this.userName + " 进入了聊天室", true);
            } catch (IOException e) {
                System.out.println("get input and output stream with error, error is " + e);
                release();
            }
        }


        // 接收消息
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

        // 发送消息
        private void send(String msg) {
            try {
                dos.writeUTF(msg);
                dos.flush();
            } catch (IOException e) {
                System.out.println("send msg with error, error is " + e);
                release();
            }
        }

        // 群聊，发给其他人，获取自己的消息，发给其他人
        // 私聊数据格式@XXX:msg
        private void sendOthers(String msg, boolean isSystemMsg) {
            boolean isPrivate = msg.startsWith("@");
            if (isPrivate) { // 私聊
                // 获取目标和数据
                int index = msg.indexOf(":");
                String targetName = msg.substring(1, index);
                msg = msg.substring(index + 1);
                for (Channel other : all) {
                    if (other.userName.equals(targetName)) {
                        other.send(this.userName + " 悄悄地对您说：" + msg);
                    }
                }

            } else { // 群聊和系统消息
                for (Channel other : all) {
                    if (other == this) { // 自己
                        continue;
                    }
                    if (!isSystemMsg) { // 群聊消息
                        other.send(this.userName + "对所有人说：" + msg);
                    } else {// 系统消息
                        other.send(msg);
                    }
                }
            }

        }

        // 释放资源
        private void release() {
            this.isRunning = false;
            CloseUtils.close(dis, dos, client);
            all.remove(this);
            sendOthers(this.userName + "离开了聊天室", true);
        }

        @Override
        public void run() {
            while (isRunning) {
                String msg = receive();
                if (!msg.equals("")) {
//                    send(msg);
                    sendOthers(msg, false);
                }
            }
        }
    }
}
