package com.dongfang.advanced.net.app.webserver;

import com.dongfang.advanced.net.app.webserver.servlet.Servlet;
import org.junit.Test;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class WebXmlParser {
    @Test
    public void test001() throws IOException, SAXException, ParserConfigurationException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
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

//        System.out.println("handler.getEntities() = " + handler.getEntities());
//        System.out.println("handler.getMappings() = " + handler.getMappings());
        WebContext webContext = new WebContext(handler.getEntities(), handler.getMappings());

        // 假设输入了/firstHttpServlet
        String servletClassName = webContext.getServletClassName("/helloServlet");
        Class aClass = Class.forName(servletClassName);
        Servlet servlet = (Servlet) aClass.getConstructor().newInstance();
        System.out.println(servlet.getClass());
        servlet.service();

    }

    private static class WebXmlHandler extends DefaultHandler {
        private List<Entity> entities;
        private List<Mapping> mappings;
        private Entity entity;
        private Mapping mapping;
        private String tag; //存储操作标签
        private boolean isMapping = false;

        public WebXmlHandler() {
            entities = new ArrayList<>();
            mappings = new ArrayList<>();
        }

        public List<Entity> getEntities() {
            return entities;
        }

        public List<Mapping> getMappings() {
            return mappings;
        }


        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (qName != null) {
                tag = qName;
                if (tag.equals("servlet")) {
                    entity = new Entity();
                    isMapping = false;
                } else if (tag.equals("servlet-mapping")) {
                    mapping = new Mapping();
                    isMapping = true;
                }
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            String contents = new String(ch, start, length).trim();
            if (tag != null) {
                if (isMapping) {
                    if (tag.equals("servlet-name")) {
                        mapping.setName(contents);
                    } else if (tag.equals("url-pattern")) {
                        mapping.addUrl(contents);
                    }
                } else {
                    if (tag.equals("servlet-name")) {
                        entity.setName(contents);
                    } else if (tag.equals("servlet-class")) {
                        entity.setClassName(contents);
                    }
                }
            }

        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if (qName != null) {
                if (qName.equals("servlet")) {
                    entities.add(entity);
                } else if (qName.equals("servlet-mapping")) {
                    mappings.add(mapping);
                }
            }
            tag = null;
        }
    }
}
