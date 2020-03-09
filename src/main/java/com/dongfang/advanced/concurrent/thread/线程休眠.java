package com.dongfang.advanced.concurrent.thread;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class 线程休眠 {
    // 在没有利用cpu来计算时，不要让while(true) 空转来浪费cpu，这时可以用yield或者sleep来让出cpu的使用权给其他程序
    public static void main(String[] args) {
        log.info("info");
        System.out.println("log.getClass() = " + log.getClass());
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        log.info("t1.state is {}", t1.getState());
        t1.start();
        log.info("t1.state is {}", t1.getState());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("t1.state is {}", t1.getState());
    }
}