package com.dongfang.advanced.concurrent.pool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ThreadPoolDemo {

    static class Task implements Runnable {
        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.error(Thread.currentThread().getName());
        }
    }
    public static void main(String[] args) throws ExecutionException, InterruptedException {
/*        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(1,
                2,
                3,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(3));
        threadPool.execute(new Task());
        threadPool.execute(new Task());
        threadPool.execute(new Task());

        threadPool.execute(new Task());


        threadPool.shutdown();*/

        ScheduledExecutorService pool = Executors.newScheduledThreadPool(3);
        for (int i = 0; i < 10; i++) {
            pool.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
/*                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                    log.error( Thread.currentThread().getName() + "hello----->");
                }
            }, 3, 4,TimeUnit.SECONDS);
        }
        pool.shutdown();

    }
}
