package com.dongfang.advanced.concurrent.thread.interrupt;

import java.util.Date;

/**
 * 两阶段终止模式
 *      1、在一个线程T1中如何优雅终止线程T2，这里的优雅是指给T2一个料理后事的机会
 *      2、错误思路：
 *              -- 使用线程对象的stop方法停止线程
 *                  stop方法会真正杀死线程，这时如果线程锁住了共享资源，那么当它被杀死后就再也没有机会释放锁，其他线程
 *                  将永远无法获取锁
 *              -- System.exit()方法停止线程
 *                  目的仅是停止一个线程，但是这种做法会让整个程序停止
 *
 *                  stop suspend resume 这些方法已经过时，容易破坏同步代码块，造成线程死锁
 */
public class 两阶段终止 {
    public static void main(String[] args) throws InterruptedException {
        TwoPhaseTermination tpt = new TwoPhaseTermination();
        tpt.start();

        Thread.sleep(3500);
        tpt.stop();
    }
}

class TwoPhaseTermination {
    private Thread monitor;

    // 启动监控线程
    public void start() {
        monitor = new Thread(() -> {
            while (true) {
                Thread currentThread = Thread.currentThread();
                if (currentThread.isInterrupted()) {
                    System.out.println("料理后事" + new Date());
                    break;
                }
                try {
                    // 睡眠中被打断，抛出异常，但是打断标记会被清除
                    Thread.sleep(1000);
                    // 正常被打断，打断标记为true，不会清除
                    System.out.println("执行监控记录" + new Date());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    // 抛出异常后，打断标记被置为false，因此再次打断 重新设置打断标记
//                    currentThread.interrupt();
                }
            }
        });
        monitor.start();
    }

    // 停止监控线程
    public void stop() {
        monitor.interrupt();
    }
}