package com.dongfang.advanced.net.protocol.tcp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 文件上传服务器；单向
 */
public class FileServer {
    public static void main(String[] args) throws IOException {
        System.out.println("----------------------FILE_SERVER---------------------------");

        // 1、指定商品，使用ServerSocket创建服务器
        ServerSocket server = new ServerSocket(31945);

        // 2、阻塞式等待连接 accept
        Socket client = server.accept();
        // http://localhost:31945就会建立连接

        // 3、操作：输入输出流操作
        InputStream is = new BufferedInputStream(client.getInputStream());
        OutputStream os = new BufferedOutputStream(new FileOutputStream("C:\\Users\\Li Dongfang\\Desktop\\0713\\cp.txt"));
        byte[] buffer = new byte[1024];
        int len;
        while ((len = is.read(buffer)) != -1) {
            os.write(buffer, 0, len);
            System.out.println("len = " + len);
        }
        os.flush();


//        DataOutputStream dos = new DataOutputStream(client.getOutputStream());
//        dos.writeUTF("上传成功");
//        dos.flush();


        // 4、释放资源
        os.close();
        is.close();
//        dos.close();
        client.close();

    }
}
