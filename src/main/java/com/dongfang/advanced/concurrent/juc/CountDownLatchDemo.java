package com.dongfang.advanced.concurrent.juc;

import java.util.concurrent.CountDownLatch;

/**
 * 计数器
 * 原理：
 *      1、数量减1 countDown
 *      2、等待计算器归零，然后再向下执行
 *          每次有线程调用countDown()数量减1 假设计算器变为0，countDownLatch.await()就会被唤醒，断续执行
 */
public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        // 倒计时，必须要执行任务的时候，再使用
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " go out");
                System.out.println("countDownLatch.getCount() = " + countDownLatch.getCount());
                countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }
        // 等待计数器归零，再向下执行，6个线程跑完了再执行后续的操作
        countDownLatch.await();

        System.out.println("close door");

    }
}
