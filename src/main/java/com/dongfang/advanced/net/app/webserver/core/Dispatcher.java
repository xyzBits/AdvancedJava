package com.dongfang.advanced.net.app.webserver.core;

import com.dongfang.advanced.util.CloseUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class Dispatcher implements Runnable {
    private Socket client;
    private Request request;
    private Response response;

    public Dispatcher(Socket client) {
        this.client = client;
        try {
            request = new Request(client);
            response = new Response(client);
        } catch (IOException e) {
            e.printStackTrace();
            CloseUtils.close(client);
        }
    }

    @Override
    public void run() {
        try {
            String url = request.getUrl();
            if (url == null || url.isEmpty()) {
                InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("D:\\ubuntu\\learn\\JavaWeb\\MavenProject\\maven03\\AdvancedJava\\src\\main\\resources\\html\\index.html");
                response.print(new String(is.readAllBytes()));
                response.pushToBrowser(200);
            }
            Servlet servlet = WebApp.getServletByUrl(url);
            if (servlet != null) {
                servlet.service(request, response);
                response.pushToBrowser(200);
            } else {
                // 错误404页面
                InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("D:\\ubuntu\\learn\\JavaWeb\\MavenProject\\maven03\\AdvancedJava\\src\\main\\resources\\html\\404.html");
                response.print(new String(is.readAllBytes()));
                response.pushToBrowser(404);
            }
        } catch (Exception e) {
            try {
                response.println("");
                response.pushToBrowser(500);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        CloseUtils.close(client);

    }
}
