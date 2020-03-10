package com.dongfang.advanced.concurrent.thread.interrupt;

public class 打断正常运行的线程 {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            Thread thread = Thread.currentThread();

            while (true) {
                boolean interrupted = thread.isInterrupted();
                System.out.println("running");
                if (interrupted) {
                    System.out.println("打断了，你还要干什么" + interrupted);
                    break;
                }
            }

        }, "t1");
        t1.start();

        sleep(50);
        t1.interrupt();
    }

    private static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}