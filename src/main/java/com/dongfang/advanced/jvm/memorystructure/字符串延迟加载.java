package com.dongfang.advanced.jvm.memorystructure;

/**
 * new String()先去串池中，没有的话，创建一个，然后在堆中创建
 *                       如果有的话，堆里还是要创建一个目
 *
 *                       +操作 常量的话，编译器直接拼接，
 *                              变量的话，将左边第一个变为StringBuilder，再调用append()方法
 */
public class 字符串延迟加载 {
    public static void main(String[] args) {
        int x = args.length;
        System.out.println();
        System.out.println("1");
        System.out.println("2");
        System.out.println("3");
        System.out.println("4");
        System.out.println("5");
        System.out.println("6");
        System.out.println("7");
        System.out.println("8");
        System.out.println("9");
        // 从上到下，执行一行，加载一个字符串到串池

        // 再重复执行，串池中已经存在这些对象，因此不会再加载了，直接从串池中取
        System.out.println("1");
        System.out.println("2");
        System.out.println("3");
        System.out.println("4");
        System.out.println("5");
        System.out.println("6");
        System.out.println("7");
        System.out.println("8");
        System.out.println("9");


    }
}
