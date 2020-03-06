package com.dongfang.advanced.jvm.memorystructure;

/**
 * 演示 cpu 占用过高
 * 定位
 * 用top定位哪个进程对cpu的占用过高
 * ps H -eo pid,tid,%cpu | grep 进程id （用ps命令进一步定位是哪个线程引起的cpu占用过高）
 * jstack 进程id
 * 可以根据线程id 找到有问题的线程，进一步定位到问题代码的源码行号
 */
public class 线程导致Cpu过高 {

    public static void main(String[] args) {
        new Thread(null, () -> {
            System.out.println("1...");
            while (true) {

            }
        }, "thread1").start();


        new Thread(null, () -> {
            System.out.println("2...");
            try {
                Thread.sleep(1000000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "thread2").start();

        new Thread(null, () -> {
            System.out.println("3...");
            try {
                Thread.sleep(1000000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "thread3").start();
    }
}
