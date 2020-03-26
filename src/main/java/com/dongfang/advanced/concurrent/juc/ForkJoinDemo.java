package com.dongfang.advanced.concurrent.juc;

import java.util.concurrent.RecursiveTask;

/**
 *
 * fork join特点：工作窃取
 *
 * 如何使用fork join
 *      1、通过fork join pool来执行
 *      2、计算任务 forkJoinPool.execute(ForkJoinTask task)
 *      3、计算类继承 ForkJoinTask
 */
public class ForkJoinDemo extends RecursiveTask<Long> {
    private Long start;
    private Long end;
    private Long temp = 10000L;

    public ForkJoinDemo() {
    }

    public ForkJoinDemo(Long start, Long end) {
        this.start = start;
        this.end = end;
    }

    /**
     * 计算方法
     */
    @Override
    protected Long compute() {
        if ((end - start) < temp) {
            // 小于临界值，直接计算
            long sum = 0;
            for (long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        } else { // 大于临界值，拆分计算 fork join
            long mid = (start + end) / 2;
            ForkJoinDemo task1 = new ForkJoinDemo(start, mid);
            // 拆分任务，把任务压入线程队列
            task1.fork();
            ForkJoinDemo task2 = new ForkJoinDemo(mid + 1, end);
            task2.fork();

            return task1.join() + task2.join();
        }
    }

}