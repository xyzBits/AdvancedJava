package com.dongfang.advanced.concurrent.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 精确控制 A 执行完调用B，B执行完调用C，C执行完调用A
 */
public class ThreadCommunicateByJucPrecise {
    public static void main(String[] args) {
        DataJucPrecise data = new DataJucPrecise();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data.printA();
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data.printB();
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data.printC();
            }
        }, "C").start();
        /**
         * 结果是随机的，我想控制为有序，精准的通知和唤醒线程
         */
    }
}


/**
 * 不能操作了，就等待，操作完了，通知其他线程
 * <p>
 * 等待  -- 判断是否要等待
 * 业务
 * 通知  -- 业务做完了通知
 */
class DataJucPrecise {
    private Lock lock = new ReentrantLock();
    private Condition conditionA = lock.newCondition();
    private Condition conditionB = lock.newCondition();
    private Condition conditionC = lock.newCondition();

    private int number = 1; // 1A 2B 3C
    // condition.await()等待

    public void printA() {
        lock.lock();
        try {
            while (number != 1) {
                conditionA.await();
            }
            System.out.println(Thread.currentThread().getName() + "----> AAAA");
            // 唤醒指定的线程，线程A执行完了，唤醒B
            number = 2;
            conditionB.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printB() {
        lock.lock();
        try {
            while (number != 2) {
                conditionB.await();
            }
            System.out.println(Thread.currentThread().getName() + "----> BBBB");
            // 这时number设置为3，唤醒线程C去执行
            number = 3;
            conditionC.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printC() {
        lock.lock();
        try {
            while (number != 3) {
                conditionC.await();
            }
            System.out.println(Thread.currentThread().getName() + "----> CCCC");
            number = 1;
            conditionA.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}