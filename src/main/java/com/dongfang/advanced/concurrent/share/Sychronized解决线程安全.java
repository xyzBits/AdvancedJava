package com.dongfang.advanced.concurrent.share;

/**
 * Synchronized解决方案
 *      应用之互斥
 *          为了避免发生临界区的竞态我们的发生，有多种手段和目的可以达到
 *              1、阻塞式解决方法：synchronized lock
 *              2、非阻塞式：原子变量
 *          这里使用阻塞式的解决方案，synchronized来解决上述问题，俗称为对象锁，
 *          它采用互斥的方式让同一时刻至多只有一个线程能持有对象锁，其他线程想获取这个对象锁时，
 *          会被 阻塞，这样就能保证拥有锁的线程可以安全地执行临界区内的代码，不用担心上下文切换
 *
 *
 *          注意：互斥和同步都可以用synchronized完成，但是有区别
 *              互斥是保证只有一个线程执行临界区代码
 *              同步是由于线程执行的先后顺序不同，需要一个线程等待其他线程运行到某个点
 *
 *              对象任意，多个线程要使用同一个对象，临界区内代码单线程执行
 *              synchronize(对象) { 线程1 线程2（blocked) 线程1完成，释放锁，唤醒其他线程
 *                  临界区
 *              }
 */
public class Sychronized解决线程安全 {
    // 两个线程对初始值为0的静态变量做一个自增，一个做自减，各做5000
    private static int counter = 0;
    private static final Object room = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                synchronized (room) {
                    counter++;
                }
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                synchronized (room) {
                    counter--;
                }
            }
        }, "t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("counter = " + counter);
    }
}