package com.dongfang.advanced.concurrent.juc;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 异步调用
 *      1、异步执行
 *      2、成功回调
 *      3、失败回调
 */
public class FutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /*ExecutorService executorService = Executors.newFixedThreadPool(10);
        CompletionService<String> completionService = new ExecutorCompletionService<>(executorService);*/

        // 没有返回值的异步回调
        // 发起一个请求
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " runAsync");
        });

        System.out.println("------------");
        // 获取执行结果
        Void aVoid = completableFuture.get();

        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " supplyAsync");
            int i = 10 / 0;
            return new Random().nextInt();
        });

        Integer ans = future.whenComplete((t, u) -> {
            System.out.println("t = " + t);
            System.out.println("u = " + u);
        }).exceptionally(e -> {
            e.printStackTrace();
            return -1; // 可以获取到错误的返回结果
        }).get();

        System.out.println("ans = " + ans);
        /**
         * t = null
         * u = java.util.concurrent.CompletionException: java.lang.ArithmeticException: / by zero
         * java.util.concurrent.CompletionException: java.lang.ArithmeticException: / by zero
         * Caused by: java.lang.ArithmeticException: / by zero
         *     at com.dongfang.advanced.concurrent.function.FutureDemo.lambda$main$1(FutureDemo.java:45)
         *     at java.util.concurrent.CompletableFuture$AsyncSupply.run(CompletableFuture.java:1604)
         *     ... 5 more
         * ans = -1
         */

    }
}