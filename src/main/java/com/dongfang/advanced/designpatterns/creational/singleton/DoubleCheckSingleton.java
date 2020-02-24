package com.dongfang.advanced.designpatterns.creational.singleton;

/**
 * 双重检查锁的实现
 *      这个模式将同步内容下放到if内部，提高了执行效率，不必每次获取对象时都进行同步，只有第一次同步，创建了以后就没有必要同步了
 */
public class DoubleCheckSingleton {
    private static DoubleCheckSingleton instance = null;

    private DoubleCheckSingleton() {
    }

    public static DoubleCheckSingleton getInstance() {
        if (instance == null) {
            // 每一次new的时候才同步
            synchronized (DoubleCheckSingleton.class) {
                if (instance == null) {
                    instance = new DoubleCheckSingleton();
                }
            }
        }

        return instance;
    }
}
