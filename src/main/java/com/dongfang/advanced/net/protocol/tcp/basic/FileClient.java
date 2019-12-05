package com.dongfang.advanced.net.protocol.tcp.basic;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 文件下载服务器：单向
 */
public class FileClient {
    public static void main(String[] args) throws IOException {
        System.out.println("----------------------FILE_CLIENT---------------------------");

        //  1、指定服务器的地址和端口，使用Socket创建客户端
        Socket client = new Socket("localhost", 31945);

        //  2、操作：输入输出流操作
        InputStream is = new BufferedInputStream(new FileInputStream("C:\\Users\\Li Dongfang\\Desktop\\0713\\IMG_1515.JPG"));
        OutputStream os = new BufferedOutputStream(client.getOutputStream());
        byte[] buffer = new byte[1024];
        int len;
        while ((len = is.read(buffer)) != -1) {
            System.out.println("test , len = " + len);
            os.write(buffer, 0, len);
        }
        os.flush();

        // 为什么卡在这里
//        BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
//        String msg = reader.readLine();
//        System.out.println("msg = " + msg);

        //  3、释放资源
        os.close();
        is.close();
//        reader.close();
        client.close();
    }
}
