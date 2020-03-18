package com.dongfang.advanced.concurrent.tools.threadpool;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueue<T> {
    // 1、任务队列
    private Queue<T> queue = new ArrayDeque<>();

    // 2、锁 保护队头元素
    private ReentrantLock lock = new ReentrantLock();

    // 3、生产者条件变量
    private Condition fullWaitSet = lock.newCondition();
    // 4、消费者条件变量
    private Condition emptyWaitSet = lock.newCondition();

    // 5、容量
    private int capacity;

}
