package com.dongfang.advanced.tools.log;

import org.apache.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.junit.Test;


public class LoggerDemo {
    static final Logger LOGGER4J = Logger.getLogger(LoggerDemo.class);
    @Test
    public void testLog4j() {
        LOGGER4J.info("hello log4j demo");
    }

    static final org.apache.logging.log4j.Logger LOGGER4J2 = LogManager.getLogger(LoggerDemo.class);
    @Test
    public void testLo4j2() {
        LOGGER4J2.info("hello log4j2 demo");
    }


    static final org.slf4j.Logger LOGGER_BACK = org.slf4j.LoggerFactory.getLogger(LoggerDemo.class);
    @Test
    public void testLogBack() {
        LOGGER_BACK.error("hello log back demo");
    }
}
