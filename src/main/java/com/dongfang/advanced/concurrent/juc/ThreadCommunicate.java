package com.dongfang.advanced.concurrent.juc;

public class ThreadCommunicate {
    public static void main(String[] args) {
        Data data = new Data();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    data.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    data.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    data.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "C").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    data.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "D").start();
    }
}


/**
 * 不能操作了，就等待，操作完了，通知其他线程
 *
 * 等待  -- 判断是否要等待
 * 业务
 * 通知  -- 业务做完了通知
 */
class Data {
    private int number = 0;

    public synchronized void increment() throws InterruptedException {
//        if (number != 0) {
        while (number != 0) {
            // 等待操作
            // 调用wait()方法后，线程进入等待状态，wait()方法不会返回，直到将来某个时刻，
            // 线程从等待状态被其他线程唤醒后，wait()方法才会返回，然后，继续执行下一条语句。
            // 要始终在while循环中wait()，并且每次被唤醒后拿到this锁就必须再次判断
            // 以下是查到的资料
            //
            //结论：就是用if判断的话，唤醒后线程会从wait之后的代码开始运行，但是不会重新判断if条件，
            // 直接继续运行if代码块之后的代码，而如果使用while的话，也会从wait之后的代码运行，但是唤醒后会重新判断循环条件，
            // 如果不成立再执行while代码块之后的代码块，成立的话继续wait。
            //
            //这也就是为什么用while而不用if的原因了，因为线程被唤醒后，执行开始的地方是wait之后。
            this.wait();
        }
        // 等于0 直接++
        number++;
        System.out.println(Thread.currentThread().getName() + "----> " + number);
        // 通知其他线程，++ 完毕
        this.notifyAll();
    }

    public synchronized void decrement() throws InterruptedException {
//        if (number == 0) { while防止虚假唤醒
        while (number == 0) {
            // = 0 时，等待操作
            this.wait();
        }

        // 不等于0 直接--
        number--;
        System.out.println(Thread.currentThread().getName() + "----> " + number);
        // 通知其他线程 -- 完毕
        this.notifyAll();
    }
}