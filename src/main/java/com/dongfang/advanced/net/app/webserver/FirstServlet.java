package com.dongfang.advanced.net.app.webserver;

import com.dongfang.advanced.net.app.webserver.servlet.Servlet;

public class FirstServlet implements Servlet {
    @Override
    public void service() {
        System.out.println("FirstServlet");
    }
}
