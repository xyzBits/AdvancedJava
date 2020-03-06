package com.dongfang.advanced.jvm.memorystructure;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Heap 堆
 * 通过 new 关键字，创建对象都会使用堆内存
 * 特点
 * 它是线程共享的，堆中对象都需要考虑线程安全的问题
 * 有垃圾回收机制
 */
public class 堆内存溢出 {
    /**
     * java.lang.OutOfMemoryError: Java heap space
     * -Xmx8m设置堆内存空间大小
     */
    @Test
    public void testHeapOutOfMemory() {
        int i = 0;
        try {
            List<String> list = new ArrayList<>();
            String a = "hello";
            while (true) {
                list.add(a); // hello, hellohello, hellohellohellohello ...
                a = a + a;  // hellohellohellohello
                i++;
            }
        } catch (Throwable e) {
            e.printStackTrace();
            System.out.println(i);
        }
    }

    /**
     * 堆内存诊断
     * 1. jps 工具
     * 查看当前系统中有哪些 java 进程
     * 2. jmap 工具
     * 查看堆内存占用情况 jmap - heap 进程id
     * 3. jconsole 工具
     * 图形界面的，多功能的监测工具，可以连续监测
     * @throws InterruptedException
     */
    @Test
    public void 堆内存诊断() throws InterruptedException {
        System.out.println("1...");
        Thread.sleep(30000);
        byte[] array = new byte[1024 * 1024 * 10]; // 10 Mb
        System.out.println("2...");
        Thread.sleep(20000);
        array = null;
        System.gc();
        System.out.println("3...");
        Thread.sleep(1000000L);
    }
}
