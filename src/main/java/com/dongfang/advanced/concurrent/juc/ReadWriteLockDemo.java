package com.dongfang.advanced.concurrent.juc;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁：
 *       读的时候可以多线程同时去读
 *       写的时候只有一个线程去写
 *       读 读 可以共存
 *       读 写 不可以共存
 *       写 写 不可以共存
 *       独占锁，一次只能被一个线程占有，写锁
 *       共享锁，多个线程同时占有 读锁
 */
public class ReadWriteLockDemo {
    /**
     * 缓存
     */
    private static class MyCache {
        private volatile Map<String, Object> map = new HashMap<>();
        // 读写锁，更加细粒度的操作
        private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        private final Lock readLock = readWriteLock.readLock();
        private final Lock writeLock = readWriteLock.writeLock();

        /**
         * 存，写，写入的时候，只希望同时只有一个线程写
         */
        public void put(String key, Object value) {
            writeLock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + " 写入 " + key);
                map.put(key, value);
                System.out.println(Thread.currentThread().getName() + " 写入 OK " + key);
            } finally {
                writeLock.unlock();
            }
        }

        /**
         * 取，读，可以有多个线程读
         */
        public Object get(String key) {
            readLock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + " 读取 " + key);
                Object ans = map.get(key);
                System.out.println(Thread.currentThread().getName() + " 读取 OK " + key);
                return ans;
            } finally {
                readLock.unlock();
            }
        }
    }

    public static void main(String[] args) {

        MyCache cache = new MyCache();
        for (int i = 1; i <= 5; i++) {
            final String temp = i + "";
            new Thread(() -> {
                cache.put(temp, new Object());
            }, "Thread-" + i).start();
        }

        for (int i = 1; i <= 5; i++) {
            final String temp = i + "";
            new Thread(() -> {
                cache.get(temp);
            }, "Thread-" + i).start();
        }
    }
}