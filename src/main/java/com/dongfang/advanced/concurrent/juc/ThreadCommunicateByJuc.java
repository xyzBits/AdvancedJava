package com.dongfang.advanced.concurrent.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadCommunicateByJuc {
    public static void main(String[] args) {
        DataJuc data = new DataJuc();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    data.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    data.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    data.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "C").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    data.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "D").start();
        /**
         * 结果是随机的，我想控制为有序，精准的通知和唤醒线程
         */
    }
}


/**
 * 不能操作了，就等待，操作完了，通知其他线程
 *
 * 等待  -- 判断是否要等待
 * 业务
 * 通知  -- 业务做完了通知
 */
class DataJuc {
    private int number = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    // condition.await()等待
    // condition.signalAll()唤醒全部

    public void increment() throws InterruptedException {
        lock.lock();
        try {
            while (number != 0) {
                // 等待操作
                condition.await();
            }
            // 等于0 直接++
            number++;
            System.out.println(Thread.currentThread().getName() + "----> " + number);

            // 通知其他线程，++ 完毕
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void decrement() throws InterruptedException {
        lock.lock();
        try {
            while (number == 0) {
                // = 0 时，等待操作
                condition.await();
            }

            // 不等于0 直接--
            number--;
            System.out.println(Thread.currentThread().getName() + "----> " + number);

            // 通知其他线程 -- 完毕
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}