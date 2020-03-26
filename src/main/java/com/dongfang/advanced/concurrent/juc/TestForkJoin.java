package com.dongfang.advanced.concurrent.juc;

import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

public class TestForkJoin {
    private Long start = 0L;
    private Long end = 10_0000_0000L;

    @Test
    public void testSum() {
        long sum = 0;
        for (long i = start; i < end; i++) {
            sum += i;
        }
        System.out.println("sum = " + sum);
    }

    @Test
    public void testForkJoin() throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinDemo task = new ForkJoinDemo(start, end);
//        forkJoinPool.execute(task);
        ForkJoinTask<Long> submit = forkJoinPool.submit(task);
        Long result = submit.get();
        System.out.println("result = " + result);
    }

    @Test
    public void testStream() {
        long ans = LongStream.range(start, end).parallel().sum();
        System.out.println("ans = " + ans);
    }
}