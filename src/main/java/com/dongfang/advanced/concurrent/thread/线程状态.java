package com.dongfang.advanced.concurrent.thread;

/**
 * 线程状态
 *      1、初始状态：仅是在语言层面创建了线程对象，还未与操作系统线程关联
 *      2、可运行状态：就绪状态，该线程已经被创建，与操作系统线程关联，可以由cpu调度执行
 *      3、运行状态：指获取了cpu时间片运行中的状态
 *              当cpu时间片用完，就会从运行状态转到时可运行状态，会导致线程的上下文切换
 *      4、阻塞状态：如果调用了阻塞的api，例如bio读写文件，这里线程实际不会用到cpu，会导致线程上下文切换，进入阻塞状态
 *                  bio操作完，操作系统唤醒阻塞线程，转换至可运行状态
 *                  时间片不会分配给阻塞状态的线程
 *                  与可运行状态的区别是，阻塞状态的线程，如果不唤醒，调度器一直不会考虑它们
 *      5、终止状态：线程执行完毕，生命周期结束，不会再转换为其他状态
 *
 *          1、blocked
 *          2、waiting join
 *          3、timed_waiting sleep
 */
public class 线程状态 {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            System.out.println("running...");
        }, "t1");

        Thread t2 = new Thread(() -> {
            while (true) {

            }
        }, "t2");
        t2.start();

        Thread t3 = new Thread(() -> {
            System.out.println("running...");
        }, "t3");
        t3.start();

        Thread t4 = new Thread(() -> {
            synchronized (线程状态.class) {
                try {
                    Thread.sleep(10000000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t4");
        t4.start();

        Thread t5 = new Thread(() -> {
            try {
                t2.join(10000); // waiting 没有time，就是waiting，如果设置了时间，就是timed_waiting
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t5");
        t5.start();

        Thread t6 = new Thread(() -> {
            // t4已经拿了这个锁，所以在这等待，获得不了
            synchronized (线程状态.class) {
                try {
                    Thread.sleep(10000000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t6");
        t6.start();

        System.out.println("t1.getState() = " + t1.getState());
        System.out.println("t2.getState() = " + t2.getState());
        System.out.println("t3.getState() = " + t3.getState());
        System.out.println("t4.getState() = " + t4.getState());
        System.out.println("t5.getState() = " + t5.getState());
        System.out.println("t6.getState() = " + t6.getState());
        /**
         * t1.getState() = NEW
         * t2.getState() = RUNNABLE
         * t3.getState() = TERMINATED
         * t4.getState() = TIMED_WAITING
         * t5.getState() = WAITING
         * t6.getState() = BLOCKED
         */
    }
}