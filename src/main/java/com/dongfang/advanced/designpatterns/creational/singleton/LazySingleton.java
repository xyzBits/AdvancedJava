package com.dongfang.advanced.designpatterns.creational.singleton;

import java.io.ObjectStreamException;
import java.io.Serializable;

public class LazySingleton implements Serializable {
    private static LazySingleton instance = null; // 静态变量必须初始化

    private LazySingleton() {
        // 这样如果先通过反射调用，还是有漏洞的
        if (instance != null) throw new RuntimeException();
    }

    // 整个方法都被同步了，效率较低
    public static synchronized LazySingleton getInstance() {
        if (instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }

    // 反序列化，如果定义了这个方法，直接返回此方法指定的对象，而不是创建新的对象
    private Object readResolve() throws ObjectStreamException {
        return instance;
    }
}
