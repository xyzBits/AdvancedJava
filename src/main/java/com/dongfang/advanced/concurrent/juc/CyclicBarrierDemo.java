package com.dongfang.advanced.concurrent.juc;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 加法计数器
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        // 执行完7个线程，才执行后面runnable中的任务，如果不够，就一直等待，直到满了
        CyclicBarrier cyclicBarrier = new CyclicBarrier(9, () -> {
            System.out.println("call dragon success");
        });
        // 召唤龙珠的线程
        for (int i = 1; i <= 7; i++) {
            final int temp = i;
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " collect " + temp + " dragon zhu");
                try {
                    // 这里做加法
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }
    }
}