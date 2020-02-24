package com.dongfang.advanced.designpatterns.creational.singleton;

public class LazySingleton {
    private static LazySingleton instance = null; // 静态变量必须初始化

    private LazySingleton() {
    }

    // 整个方法都被同步了，效率较低
    public static synchronized LazySingleton getInstance() {
        if (instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }
}
