package com.dongfang.advanced.concurrent.share;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class 方法上的Synchronized {
    public static void main(String[] args) {
        log.error("hello");
    }
    public synchronized void test1() {
    }
    // 等价于
    public void test11() {
        synchronized (this) {
        }
    }

    public synchronized static void test2() {
    }
    // 等价于
    public static void test22() {
        synchronized (方法上的Synchronized.class) {
        }
    }
}
