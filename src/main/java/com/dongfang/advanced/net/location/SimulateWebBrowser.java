package com.dongfang.advanced.net.location;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class SimulateWebBrowser {
    @Test
    public void testWebSpider() throws IOException {
        // 1、获取URL
        URL url = new URL("https://www.dianping.com");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        // 设置这个头信息后，服务器会认为是浏览器发送的请求
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.97 Safari/537.36");


        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        String msg;
        while ((msg = br.readLine()) != null) {
            System.out.println(msg);
        }

        br.close();





        // 2、下载资源

        // 3、分析
    }
}
