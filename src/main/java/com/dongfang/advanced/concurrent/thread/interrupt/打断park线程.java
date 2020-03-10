package com.dongfang.advanced.concurrent.thread.interrupt;

import java.util.Date;
import java.util.concurrent.locks.LockSupport;

public class 打断park线程 {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            System.out.println("park..." + new Date());
            LockSupport.park();
            System.out.println("unpark..." + new Date());
            System.out.println("打断状态为 " + Thread.currentThread().isInterrupted() + "" + new Date());

            Thread.interrupted(); // 这个方法会将打断标记设为false，再次park生效
            // 被打断一次后，无法再park，park失效
            LockSupport.park();
            System.out.println("unpark..." + new Date());

        }, "t1");
        t1.start();

        Thread.sleep(1000);
        t1.interrupt();
    }
}