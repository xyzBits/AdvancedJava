package com.dongfang.advanced.net.app.webserver.core;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class WebApp {
    private static WebContext webContext;

    static {
        try {
            // SAX解析
            // 1、获取解析工厂
            SAXParserFactory factory = SAXParserFactory.newInstance();

            // 2、从解析工厂获取解析器
            SAXParser parser = factory.newSAXParser();

            // 3、编写处理器

            // 4、加载文档document注册处理器
            WebXmlHandler handler = new WebXmlHandler();

            // 5、解析
            parser.parse("D:\\ubuntu\\learn\\JavaWeb\\MavenProject\\maven03\\AdvancedJava\\src\\main\\resources\\xml-conf\\web.xml", handler);

            webContext = new WebContext(handler.getEntities(), handler.getMappings());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Servlet getServletByUrl(String url) {
        // 假设输入了/firstHttpServlet
        String servletClassName = webContext.getServletClassName(url);
        Class aClass = null;
        try {
            aClass = Class.forName(servletClassName);
            return (Servlet) aClass.getConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
