package com.dongfang.advanced.concurrent.juc;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * 同步队列
 *      没有容量
 *          进去一个元素，必须等待取出来之后，才能往里面放一个元素
 *      和其他blocking queue不一样，因为它不存储元素，只要put了一个元素，必须从里面take，不然无法put
 *T1 put ---> 1
 * T2get --->1
 * T1 put ---> 2
 * T2get --->2
 * T1 put ---> 3
 * T2get --->3
 */
public class SynchronousQueueDemo {
    public static void main(String[] args) {
        SynchronousQueue<Integer> queue = new SynchronousQueue<>();
        new Thread(() -> {
            try {
                for (int i = 1; i <= 3; i++) {
                    System.out.println(Thread.currentThread().getName() + " put ---> " + i);
                    queue.put(i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }, "T1").start();

        new Thread(() -> {
            try {
                for (int i = 1; i <= 3; i++) {
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println(Thread.currentThread().getName() + "get --->" + queue.take());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "T2").start();
    }
}