package com.dongfang.advanced.concurrent.thread.interrupt;

import java.util.Date;

public class 打断阻塞状态线程 {
    private static boolean isRunning = true;
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while (isRunning) {
                sleep(5000);
                System.out.println(Thread.currentThread().getName() + " time is " + new Date());
            }
        }, "t1");
        t1.start();

        // 等待t1线程进行sleep
        sleep(2000);
        // 主线程去打断，打断后会有一个标记，标记这个线程是否被 其他线程打断过
        t1.interrupt();
        // 结果为false，因为t1.sleep 后才打断的，标记会被清除
        // sleep wait join以异常的方式表示被打断过，异常本标志被清除
        System.out.println("t1.isInterrupted() = " + t1.isInterrupted());


    }


    private static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            isRunning = false;
            e.printStackTrace();
        }
    }
}