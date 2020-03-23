package com.dongfang.advanced.concurrent.pool;

import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class TestQueue {
    @Test
    public void testNonBlockingQueue() {
        // 无边界队列
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        queue.offer("java");
        queue.offer("python");
        queue.offer("cpp");
        while (!queue.isEmpty()) {
            System.out.println("queue.poll() = " + queue.poll());
        }
    }

    @Test
    public void testBlockingQueue() {
        BlockingQueue<String> queue = new LinkedBlockingQueue<>(3);
        queue.offer("java");
        queue.offer("python");
        queue.offer("cpp");
        try {
            // 3秒内能不以放进去
            queue.offer("csharp", 3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("queue.size() = " + queue.size());
        System.out.println("queue.poll() = " + queue.poll());
        System.out.println("queue.size() = " + queue.size());
    }

}
