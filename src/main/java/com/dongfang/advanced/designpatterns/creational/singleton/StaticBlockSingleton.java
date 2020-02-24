package com.dongfang.advanced.designpatterns.creational.singleton;

/**
 * 外部类没有static属性，则不会像饿汉式那样立即加载对象
 * 只有真正调用getInstance()，静态代码块，加载类是线程安全的，加载类的时候就加载
 */

public class StaticBlockSingleton {
    private static StaticBlockSingleton instance = null;

    private StaticBlockSingleton() {}
    static {
        System.out.println("\"加载了\" = " + "加载了");
        instance = new StaticBlockSingleton();
    }

    public static StaticBlockSingleton getInstance() {
        System.out.println("获取对象");
        return instance;
    }



}
