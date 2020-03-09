package com.dongfang.advanced.concurrent.thread;

public class 线程等待join {
    private static int num = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println("开始");
            sleep(1000);
            System.out.println("结束");
            num = 10;
        });
        thread.start();
        /**
         * 应用同步
         *      以调用方角度来讲，如果
         *          需要等待结果返回，才能继续运行就是同步
         *          不需要等待结果返回，就能继续运行就是异步
         */
        // 等待线程运行结束，哪个对象调用，就等待哪个
        thread.join(); // thread 运行结束后，主线程才继续执行
        System.out.println("结果 为 " + num);
        System.out.println("结束");
    }

    private static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}