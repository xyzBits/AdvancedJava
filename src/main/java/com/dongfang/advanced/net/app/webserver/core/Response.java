package com.dongfang.advanced.net.app.webserver.core;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class Response {
    private BufferedWriter bw;
    private StringBuilder content;
    // 状态行，请求头，回车
    private StringBuilder responseHeaders;

    private int contentLength; // 正文的字节数

    private static final String BLANK = " ";
    private static final String CRLF = "\r\n";


    private Response() {
        content = new StringBuilder();
        responseHeaders = new StringBuilder();
        contentLength = 0;
    }

    public Response(Socket client) {
        this();
        try {
            bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        } catch (IOException e) {
            responseHeaders = null;
            e.printStackTrace();
        }
    }

    public Response(OutputStream os) {
        this();
        bw = new BufferedWriter(new OutputStreamWriter(os));
    }

    public Response print(String info) {
        content.append(info);
        contentLength += info.getBytes(StandardCharsets.UTF_8).length;
        return this;
    }

    public Response println(String info) {
        return this.print(info + CRLF);
    }

    private void createHeaderInfo(int respCode) {
        responseHeaders.append("HTTP/1.1").append(BLANK).append(respCode).append(BLANK);
        switch (respCode) {
            case 200:
                responseHeaders.append("Ok").append(CRLF);
                break;
            case 404:
                responseHeaders.append("Not Found").append(CRLF);
                break;
            case 500:
                responseHeaders.append("Server Inner Error").append(CRLF);
            default:
                break;
        }

        responseHeaders.append("Date:").append(new Date()).append(CRLF);
        responseHeaders.append("Server:").append("Tomcat/1.0;charset=UTF-8").append(CRLF);
        responseHeaders.append("Content-Type:text/html").append(CRLF);
        responseHeaders.append("Content-Length:").append(contentLength).append(CRLF);
        responseHeaders.append(CRLF);
    }

    public void pushToBrowser(int respCode) throws IOException {
        if (responseHeaders == null) {
            respCode = 500;
        }
        createHeaderInfo(respCode);
        bw.append(responseHeaders);
        bw.append(content);
        bw.flush();
    }
}
