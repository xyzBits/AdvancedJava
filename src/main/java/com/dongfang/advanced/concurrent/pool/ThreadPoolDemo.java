package com.dongfang.advanced.concurrent.pool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 池化技术，事先准备好资源，有人要用，来池中拿
 * 线程池的好处：
 *      1、降低资源消耗
 *      2、提高响应的速度，不用新建，销毁线程
 *      3、方便管理线程
 *      线程复用，可以控制最大并发数，管理线程
 *      三大方法，7大参数，4种拒绝策略
 *
 *      最大线程数到底该如何定义
 *      cpu密集型  max设置为cpu核数，保证cpu效率最高
 *      io密集型   io十分占用资源，判断任务十分耗io的线程
 */
public class ThreadPoolDemo {
    public static void main(String[] args) {
        testThreadPoolParameter();
    }

    /**
     *         public ThreadPoolExecutor(int corePoolSize,                   核心线程大小
     *                               int maximumPoolSize,                     最大线程大小，queue满了
     *                               long keepAliveTime,                      超时了没人用就会释放，空闲线程等待多久
     *                               TimeUnit unit,                            时间单位
     *                               BlockingQueue<Runnable> workQueue,         阻塞队列
     *                               ThreadFactory threadFactory,               线程工厂，创建线程，一般不用
     *                               RejectedExecutionHandler handler) {        拒绝策略 queue也满了
     */
    public static void testThreadPoolParameter() {
        BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<>(3);

        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                2,
                //5, 动态获取cpu核数
                Runtime.getRuntime().availableProcessors(),
                3,
                TimeUnit.SECONDS,
                blockingQueue,
                Executors.defaultThreadFactory(),
                // new ThreadPoolExecutor.AbortPolicy() // 线程用完，queue也满了，再有任务进来，就不处理，并抛出异常 java.util.concurrent.RejectedExecutionException
                // new ThreadPoolExecutor.CallerRunsPolicy() // 哪来的，去哪里，让main线程执行main ---> OK
                // new ThreadPoolExecutor.DiscardPolicy() // 队列满了，不会抛出异常，丢掉任务
                new ThreadPoolExecutor.DiscardOldestPolicy() // 队列满了，尝试和最早的竞争，第一个是不是结束，线束了执行，没结束，抛出
        );

        /**
         * i = 5 < maxPoolSize，只会用核心线程处理
         * i = 6 queue也满了，会用核心线程以外的线程
         * 最大承载 queue.size() + max
         *
         * java.util.concurrent.RejectedExecutionException 超出后，抛出异常
         */
        for (int i = 1; i <= 9; i++) {
            threadPool.execute(() -> {
                System.out.println(Thread.currentThread().getName() + " ---> OK");
            });
        }

        threadPool.shutdown();

    }


    public static void testExecutors() {
        // 单个线程，只有一个线程
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        // 固定线程池的大小
        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(4);
        // 可伸缩
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();

        // 使用线程池创建线程
        for (int i = 0; i < 100; i++) {
            newCachedThreadPool.execute(() -> {
                // 业务
                try {
                    TimeUnit.SECONDS.sleep(4);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " OK");
            });
        }

        // 线程池用完了要关闭
        singleThreadExecutor.shutdown();
        newCachedThreadPool.shutdown();
        newFixedThreadPool.shutdown();
    }
}