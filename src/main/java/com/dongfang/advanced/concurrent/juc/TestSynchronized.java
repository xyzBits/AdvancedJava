package com.dongfang.advanced.concurrent.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程就是一个单独的资源类，没有任何附属操作，只是单独的资源，并不是一个线程类
 * 1、synchronized 内置的java关键字，Lock是一个java类
 * 2、synchronized 无法判断获取锁的状态，lock可以判断是否获取了锁，
 * 3、synchronized 会自动释放锁，lock必须手动释放，如果不释放，列锁
 * 4、synchronized t1(获得锁) t2(等待，如果t1阻塞，t2一直等待) lock锁不会一直等待下去
 * 5、synchronized 可重入锁，不可以中断，非公平，lock可重入锁，非公平，可以自己设置
 * 6、synchronized 适合锁少量的代码同步问题 lock适合锁大量的代码同步问题
 */
public class TestSynchronized {
    public static void main(String[] args) {
        // 多个线程操作同一个资源类， 多线程操作
        TicketWindow ticketWindow = new TicketWindow(200);
        // 把资源类丢入线程，或者资源类的操作
        new Thread(() -> {
            for (int i = 0; i < 6000; i++) {
                ticketWindow.sale();
            }
        }, "C").start();
        new Thread(() -> {
            for (int i = 0; i < 6000; i++) {
                ticketWindow.sale();
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 6000; i++) {
                ticketWindow.sale();
            }
        }, "B").start();

    }
}

class TicketWindow {
    private int ticketNum;
    // 非公平
    /**
     * 公平锁，十分公平，先来后到，要排队 3h 3s synchronized也是非公平
     * 非公平锁，十分不公平，可以插队
     * 第一步，生成锁
     * 第二步，加锁 lock
     * 第三步，解锁 unlock
     */
    private final Lock lock = new ReentrantLock(false);

    public TicketWindow(int ticketNum) {
        this.ticketNum = ticketNum;
    }


    public void sale() {
        lock.lock();
        boolean isLocked = lock.tryLock();
        lock.lock();
        try {
            if (ticketNum > 0) {
                try {
                    TimeUnit.MILLISECONDS.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "卖票 第 " + ticketNum-- + "---->张");
            }
        } finally {
            lock.unlock();
            lock.unlock();
            lock.unlock();
        }

    }

}