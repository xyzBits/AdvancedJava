package com.dongfang.advanced.concurrent.share;

/**
 * 问题分析：
 *      结果可能是正数，负数，零，为什么呢？因为java中对静态变量的自增不是原子操作
 *      以下通过字节码来分析
 *
 *      对于i++，实际的jvm指令如下
 *      getstatic      i 获取静态变量i的值
 *      iconst_1         准备常量1
 *      iadd              自增
 *      putstatic      i  将修改后的值存入静态变量不
 *
 *      java的内存模型如下，完成静态变量的自增，需要在主存和工作内存中进行数据交换
 *
 *              主内存 static int i = 0
 *       线程1 i++              线程2 i--
 *       在各自的线程内计算
 *       单线程，不会产生指令之间的交错，没有问题
 *       上下文与指令交错导致线程安全
 *
 *   临界区 Critical section
 *      1、一个程序运行多个线程本身没有问题
 *      2、问题出现在多个线程访问共享资源
 *          -- 多个线程读取共享资源，也没有问题
 *          -- 在多个线程对共享资源读写操作时，发生指令交错，就会出现问题
 *      3、一段代码块内如果存在对共享资源的多线程读写操作，这块代码称为临界区
 *   竞态条件：
 *      多个线程在临界区内执行，由于代码的执行顺序不同而导致结果无法预测，称之为发生了竞态条件
 */
public class 线程安全演示 {
    // 两个线程对初始值为0的静态变量做一个自增，一个做自减，各做5000
    private static int counter = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                counter++;
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                counter--;
            }
        }, "t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("counter = " + counter);
    }
}