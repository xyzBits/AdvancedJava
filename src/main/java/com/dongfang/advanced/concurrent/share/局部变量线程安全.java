package com.dongfang.advanced.concurrent.share;

public class 局部变量线程安全 {

    /**
     * 每个线程调用test1()方法时，局部变量i，会在每个线程的栈帧内存中被创建多份，因此不存在共享
     */
    private void test1() {
        int i = 10;
        i++;
    }
}