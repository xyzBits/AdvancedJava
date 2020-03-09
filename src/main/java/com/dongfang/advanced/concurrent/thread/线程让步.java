package com.dongfang.advanced.concurrent.thread;

public class 线程让步 {
    public static void main(String[] args) {
        // thread.join() 从running -> runnable
        // thread.sleep() 从running -> time_waiting

        Runnable task1 = () -> {
            int count = 0;
            while (true) {
//                try {
//                    TimeUnit.SECONDS.sleep(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                System.out.println(Thread.currentThread().getName() + " " + count++);
            }
        };
        Runnable task2 = () -> {
            int count = 0;
            while (true) {
                Thread.yield(); // 把时间片让出去
//                try {
//                    TimeUnit.SECONDS.sleep(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                System.out.println(Thread.currentThread().getName() + "------> " + count++);
            }
        };
        Thread t1 = new Thread(task1, "t1");
        Thread t2 = new Thread(task2, "t2");
        t1.start();
        t2.start();
    }
}