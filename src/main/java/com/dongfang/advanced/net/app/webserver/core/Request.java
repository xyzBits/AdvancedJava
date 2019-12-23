package com.dongfang.advanced.net.app.webserver.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Request {
    private String requestInfo;
    private String method;
    private String url;
    private String queryParam;
    private Map<String, List<String>> paramMap;
    private static final String CRLF = "\r\n";

    public Request(Socket client) throws IOException {
        this(client.getInputStream());
    }

    public Request(InputStream is) {
        paramMap = new HashMap<>();
        try {
            int available = is.available();
            System.out.println("available = " + available);
            byte[] buffer = new byte[available];
            int len;
            len = is.read(buffer);
            this.requestInfo = new String(buffer, 0, len);
            System.out.println("requestInfo = " + requestInfo);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        parseRequestInfo();
    }

    private void parseRequestInfo() {
        System.out.println("--------------分解---------------");
        // 1、获取请求方式，从开头到第一个/
        this.method = this.requestInfo.substring(0, this.requestInfo.indexOf("/")).toLowerCase();
        System.out.println("this.method = " + this.method);
        // 2、获取url 从第一个/到http/
        // 如果含有？前面的为url
        int startIndex = this.requestInfo.indexOf("/");
        int endIndex = this.requestInfo.indexOf("HTTP/");
        this.url = this.requestInfo.substring(startIndex, endIndex).trim();
        System.out.println("this.url = " + this.url);
        int queryIndex = this.url.indexOf("?");
        if (queryIndex >= 0) {
            String[] urlArray = this.url.split("\\?");
            this.url = urlArray[0].trim();
            queryParam = decode(urlArray[1], StandardCharsets.UTF_8.toString());
        }
        System.out.println("this.url = " + this.url);

        if (method.equalsIgnoreCase("post")) {
            String param = this.requestInfo.substring(this.requestInfo.lastIndexOf(CRLF)).trim();
            queryParam += "&" + param;
        }
        System.out.println("queryParam = " + queryParam);

        parseQueryParam();
    }

    private void parseQueryParam() {
        // 分割字符串&
        String[] keyValues = this.queryParam.split("&");
        for (String keyValue : keyValues) {
            // 再次分割字符串，按=
            String[] kv = keyValue.split("=");
            kv = Arrays.copyOf(kv, 2);
            String key = kv[0].trim();
            String value = kv[1] == null ? null: decode(kv[1], StandardCharsets.UTF_8.toString());
            // 存储到map中
            if (paramMap.containsKey(key)) {
                paramMap.get(key).add(value);
            } else {
                ArrayList<String> values = new ArrayList<>();
                values.add(value);
                paramMap.put(key, values);
            }
        }
    }

    private String decode(String value, String charSet) {
        try {
            return URLDecoder.decode(value, charSet);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String[] getParams(String name) {
        List<String> values = this.paramMap.get(name);
        if (values == null || values.size() < 1) {
            return null;
        }
        return values.toArray(new String[0]);
    }

    public String getParam(String name) {
        String[] values = getParams(name);
        return values == null ? null : values[0];
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, List<String>> getParamMap() {
        return paramMap;
    }
}
