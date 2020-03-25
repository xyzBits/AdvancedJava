package com.dongfang.advanced.concurrent.juc;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 信号量
 *      1、acquire 获取 获得，假设已经满了，等待被释放为止 - 1
 *      2、release 释放 会将当前的信号量释放 + 1，唤醒等待的线程
 *      多个共享资源的互斥的使用
 *      并发限流，控制最大的线程数
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        // 线程数量：停车位，限流，一次性只能进来这么多，其他的要等待
        Semaphore semaphore = new Semaphore(3);
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                // acquire 得到
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + " 抢到车位");
                    TimeUnit.SECONDS.sleep(4);
                    System.out.println(Thread.currentThread().getName() +" 离开车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    // 释放
                    semaphore.release();
                }
            }, String.valueOf(i)).start();
        }
    }
}