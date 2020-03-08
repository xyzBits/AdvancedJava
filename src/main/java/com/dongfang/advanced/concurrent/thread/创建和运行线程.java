package com.dongfang.advanced.concurrent.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 方法1 是把线程和任务合并在了一起，方法2 是把线程和任务分开了
 * 用 Runnable 更容易与线程池等高级 API 配合
 * 用 Runnable 让任务类脱离了 Thread 继承体系，更灵活
 */
@Slf4j(topic = "创建和运行线程")
public class 创建和运行线程 {
    public static void main(String[] args) {
        testFutureTask();
    }


    private static void testThread() {
        Thread thread = new Thread("由Thread创建的线程") {
            @Override
            public void run() {
                log.info("hello");
            }
        };
        thread.start();

        log.info("test");
    }

    private static void testRunnable() {
        // 任务对象
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                log.error("runnable线程内的测试");
            }
        };
        // 线程对象
        Thread thread = new Thread(runnable);
        thread.start();
        log.error("rest Runnable");
    }

    private static void testLambdaRunnable() {
        Runnable runnable = () -> {
            System.out.println("runnable lambda ");
        };

        Thread thread = new Thread(() -> {
            log.error("test lambda Runnable");
        }, "Runnable thread");
        thread.start();
        log.error("------------------");
    }



    private static void testFutureTask() {
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.error("callable return 100");
                Thread.sleep(2000);
                return 100;
            }
        };
        // FutureTask是任务对象
        FutureTask<Integer> futureTask = new FutureTask<>(callable);

        Thread thread = new Thread(futureTask, "futureTask");
        thread.start();

        Integer result = null;
        try {
            // 主线程运行到get，直到返回
            long start = System.currentTimeMillis();
            result = futureTask.get();
            long end = System.currentTimeMillis();
            log.error("time is {}", end - start);
            // time is 2005
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        log.error("result is {}", result);
    }
}
