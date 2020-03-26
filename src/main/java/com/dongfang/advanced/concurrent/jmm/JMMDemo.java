package com.dongfang.advanced.concurrent.jmm;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class JMMDemo {
    // 不加volatile，程序就会死循环
    // 加volatile 可以保证可见性
    private volatile static int num = 0;

    public static void main(String[] args) throws InterruptedException {
        testAtomicType();
    }

    public static void testVolatileVisual() throws InterruptedException {
        new Thread(() -> {
            // Thread A 不知道num的值已经在主内存中被修改
            // 线程A对主内存的变化不感知
            while (num == 0) {
            }
        }, "A").start();

        TimeUnit.SECONDS.sleep(1);
        // 已经从main线程的工作内存写回到主内存，但是线程A并不知道主内存中的值发生了变化
        num = 1;
        System.out.println("num = " + num);
    }


    /**
     * 线程A在执行任务的时候，不能被打扰，也不能被分割，要么同时成功，要么同时失败
     */
    private static void testAtomicOfVolatile() {
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    add();
                }
            }).start();
        }
        // 理论结果是20_0000 volatile无法保证原子性
        // 不加lock synchronized，如何保证原子性
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
        System.out.println(Thread.currentThread().getName() + " num = " + num);
    }

    private static void add() {
//        num++; 不是原子操作
        // 这些类的底层操作和os挂钩，在内存中直接修改值 Unsafe类是一个很特殊的存在
        atomicNum.incrementAndGet();
    }

    private static AtomicInteger atomicNum = new AtomicInteger();
    private static void testAtomicType() throws InterruptedException {
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    add();
                }
            }).start();
        }
        // 理论结果是20_0000 volatile无法保证原子性
        // 不加lock synchronized，如何保证原子性
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
        System.out.println(Thread.currentThread().getName() + " atomicNum = " + atomicNum.get());
    }
}
