package com.dongfang.advanced.jvm.memorystructure;

import java.util.ArrayList;
import java.util.List;

/**
 * 演示查看对象个数 堆转储 dump，将内存快照抓取，分析哪些对象占用空间多
 */
public class 垃圾回收后_内存占用仍然很高 {

    private static class Student {
        private byte[] big = new byte[1024 * 1024];
    }

    public static void main(String[] args) throws InterruptedException {
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            students.add(new Student());
//            Student student = new Student();
        }
        Thread.sleep(1000000000L);
    }
}
