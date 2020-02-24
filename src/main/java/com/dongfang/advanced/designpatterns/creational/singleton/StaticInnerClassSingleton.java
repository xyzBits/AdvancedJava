package com.dongfang.advanced.designpatterns.creational.singleton;

public class StaticInnerClassSingleton {
    private StaticInnerClassSingleton() {
        System.out.println("构造器");
    }

    // 静态内部类，主类加载的时候，并不会加载此类，延迟加载的优势来自这里
    // 外部类初始化时，静态内部类不会加载，实现了延时加载
    private static class LazyHolder {
        private static final StaticInnerClassSingleton instance = new StaticInnerClassSingleton();
    }

    public static StaticInnerClassSingleton getInstance() {
        System.out.println("调用了");
        return LazyHolder.instance;
    }
}
