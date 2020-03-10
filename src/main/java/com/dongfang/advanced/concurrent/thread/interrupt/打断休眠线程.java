package com.dongfang.advanced.concurrent.thread.interrupt;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
public class 打断休眠线程 {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                log.info("enter sleep... {}", new Date());
                while (true) {
                    try {
                        log.info("is running");
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        log.info("wake up {}", new Date());
                        System.out.println("e.getCause() = " + e.getCause());
                    }
                }

            }
        };
        t1.start();


        TimeUnit.DAYS.sleep(1);
        Thread.sleep(1000);

        log.info("interrupt {}", new Date());
        t1.interrupt();
        System.out.println("t1.isInterrupted() = " + t1.isInterrupted());


    }
}