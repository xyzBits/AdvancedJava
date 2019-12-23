package com.dongfang.advanced.net.app.webserver.core;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class WebXmlHandler extends DefaultHandler {
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