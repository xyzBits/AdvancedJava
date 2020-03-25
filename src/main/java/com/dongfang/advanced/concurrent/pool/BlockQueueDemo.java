package com.dongfang.advanced.concurrent.pool;

import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 什么情况下，使用阻塞队列，线程池中，队列满了，入队线程等待，队列空了，出队线程等待
 * 四组api
 *      1、抛出异常
 *      2、不会抛出异常
 *      3、阻塞等待
 *      4、超时等待
 */
public class BlockQueueDemo {
    @Test
    public void testNoBlockQueue() {
        Queue<Integer> queue =  new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            queue.offer(i);
        }
        System.out.println("queue.size() = " + queue.size());
    }

    @Test
    public void testQueueThrowExp() {
        BlockingQueue<Integer> blockingQueue = new LinkedBlockingQueue<>(3);
        System.out.println(blockingQueue.add(1));
        System.out.println(blockingQueue.add(2));
        System.out.println(blockingQueue.add(3));


        // java.lang.IllegalStateException: Queue full
        // 队列满了，抛出异常
        // System.out.println(blockingQueue.add(4));
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());

        // 队列头部没有，就抛出异常
        System.out.println(blockingQueue.element());
        // java.util.NoSuchElementException
        // 空了再取，抛出异常
        System.out.println(blockingQueue.remove());
    }

    /**
     * 有返回值，没有异常
     */
    @Test
    public void testNoExpQueue() {
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(3);
        System.out.println(queue.offer(1));
        System.out.println(queue.offer(2));
        System.out.println(queue.offer(3));
        // 队列满了，直接返回false，不抛出异常
        System.out.println(queue.offer(4));
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        // 空了再取，直接返回null
        System.out.println(queue.poll());
        //queue.peek() = null peek 方法，如果空队列，返回null
        System.out.println("queue.peek() = " + queue.peek());
    }

    /**
     * 满了，等待，一直阻塞
     */
    @Test
    public void testBlockQueue() throws InterruptedException {
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(3);
        queue.put(1);
        queue.put(2);
        queue.put(3);
        System.out.println("queue.size() = " + queue.size());
        // 队列满了，队列没有位置，会一直等待，等到能放进去
        //queue.put(4);

        System.out.println(queue.take());
        System.out.println(queue.take());
        System.out.println(queue.take());
        System.out.println("take over");
        // 队列空了，没有元素，就会一直等待
        System.out.println(queue.take());
        System.out.println("over");
    }

    @Test
    public void testTimeBlockQueue() throws InterruptedException {
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(3);
        queue.offer(1, 2, TimeUnit.SECONDS);
        queue.offer(2, 2, TimeUnit.SECONDS);
        queue.offer(3, 2, TimeUnit.SECONDS);
        System.out.println("queue.size() = " + queue.size());
        // 如果满了，等待10秒，10秒后无法添加，就退出
        queue.offer(4, 10, TimeUnit.SECONDS);
        System.out.println("---------------");
        System.out.println(queue.poll(2, TimeUnit.SECONDS));
        System.out.println(queue.poll(2, TimeUnit.SECONDS));
        System.out.println(queue.poll(2, TimeUnit.SECONDS));
        // 30秒内拿不到，就退出 了
        System.out.println(queue.poll(30, TimeUnit.SECONDS));

    }
}