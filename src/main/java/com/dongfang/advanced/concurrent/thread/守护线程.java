package com.dongfang.advanced.concurrent.thread;

import java.util.Date;

/**
 * 默认情况下，java进程需要等待所有线程都结束运行，才会结束 。有一种特殊的线程叫做守护线程，
 * 只要其他非守护线程运行结束了，即使守护线程的代码没有执行完，也会强制结束
 *
 *      注意：
 *          1、垃圾回收器线程就是一个守护线程
 *          2、Tomcat中的Acceptor Poller 都是守护线程，所以Tomcat收到shutdown命令后，不会等待它们处理完当前请求
 */
public class 守护线程 {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
            }
            System.out.println("结束 " + new Date());
        }, "t1");
        // 主线程结束了，守护线程没有执行完，也要强制结束
        t1.setDaemon(true);
        t1.start();
        Thread.sleep(1000);

        System.out.println("结束 " + new Date());
    }
}