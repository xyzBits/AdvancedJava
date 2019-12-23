package com.dongfang.advanced.net.app.webserver.core;

import org.junit.Test;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 熟悉sax的解析流程
 */
public class XmlParse {
    @Test
    public void test001() throws IOException, SAXException, ParserConfigurationException {
        // SAX解析
        // 1、获取解析工厂
        SAXParserFactory factory = SAXParserFactory.newInstance();

        // 2、从解析工厂获取解析器
        SAXParser parser = factory.newSAXParser();

        // 3、编写处理器

        // 4、加载文档document注册处理器
        PersonHandler handler = new PersonHandler();

        // 5、解析
        parser.parse("D:\\ubuntu\\learn\\JavaWeb\\MavenProject\\maven03\\AdvancedJava\\src\\main\\resources\\xml-conf\\person.xml", handler);

        System.out.println("handler.getPersons() = " + handler.getPersons());


    }

    private static class PersonHandler extends DefaultHandler {
        private List<Person> persons;
        private Person person;
        private String tag; //存储操作标签

        public List<Person> getPersons() {
            return persons;
        }

        @Override
        public void startDocument() throws SAXException {
            System.out.println("解析文档开始");
            persons = new ArrayList<>();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            System.out.println(qName + "------> 解析开始");
            if (qName != null) {
                tag = qName;
                if (tag.equals("person")) {
                    person = new Person();
                }
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            String contents = new String(ch, start, length).trim();
/*            if (contents.length() > 0) {
                System.out.println("内容为--->" + contents);
            } else {
                System.out.println("内容为--->空");
            }*/

            if (tag != null) {
                if (tag.equals("name")) {
                    person.setName(contents);
                } else if (tag.equals("age")) {
                    person.setAge(Integer.parseInt(contents));
                }
            }

        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            System.out.println(qName + "------> 解析结束");
            if (qName != null) {
                if (qName.equals("person")) {
                    persons.add(person);
                }
            }

            tag = null;
        }

        @Override
        public void endDocument() throws SAXException {
            System.out.println("解析文档结束");
        }
    }

    private static class Person {
        private String name;
        private int age;

        public Person() {
        }

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("{");
            sb.append("\"name\":\"")
                    .append(name).append('\"');
            sb.append(",\"age\":")
                    .append(age);
            sb.append('}');
            return sb.toString();
        }
    }
}
