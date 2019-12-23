package com.dongfang.advanced.net.app.webserver.servlet;

import com.dongfang.advanced.net.app.webserver.core.Request;
import com.dongfang.advanced.net.app.webserver.core.Response;
import com.dongfang.advanced.net.app.webserver.core.Servlet;

public class FirstServlet implements Servlet {

    @Override
    public void service(Request request, Response response) {
        response.print("<html>");
        response.print("<head>");
        response.print("<title>");
        response.print("服务器响应成功");
        response.print("</title>");
        response.print("</head>");
        response.print("<body>");
        response.print("欢迎回来： "  + request.getParam("uname"));
        response.print("</body>");
        response.print("</html>");

        System.out.println("测试完成");
    }
}
