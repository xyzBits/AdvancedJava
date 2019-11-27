package com.dongfang.advanced.net.location;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *   web spider 网络爬虫的原理
 *      url
 *      下载
 *      分析
 */
public class WebSpider {
    @Test
    public void testWebSpider() throws IOException {
        // 1、获取URL
        URL url = new URL("https://www.jd.com");
        // java.io.IOException: Server returned HTTP response code: 403 for URL: https://www.dianping.com
        url = new URL("https://www.dianping.com");
        InputStream is = url.openStream();

        BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        String msg;
        while ((msg = br.readLine()) != null) {
            System.out.println(msg);
        }

        br.close();





        // 2、下载资源

        // 3、分析
    }
}
