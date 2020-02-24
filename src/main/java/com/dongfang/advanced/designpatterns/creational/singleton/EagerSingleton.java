package com.dongfang.advanced.designpatterns.creational.singleton;

/**
 * 饿汉式单例设计模式
 *      由于加载类时，天然的线程安全，方法没有同步，调用效率高
 *      https://howtodoinjava.com/gang-of-four-java-design-patterns/
 */
public class EagerSingleton {
    // 类初始化时，立即加载，加载可能比较耗时
    private static final EagerSingleton instance = new EagerSingleton();

    private EagerSingleton() {
    }

    // 不需要同步，因为对象创建的时候，是类加载的时候创建的，是天然的线程安全
    // 不需要同步，调用效率高
    //
    public static EagerSingleton getInstance() {
        return instance;
    }
}
