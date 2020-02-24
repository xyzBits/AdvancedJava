package com.dongfang.advanced.designpatterns.creational.singleton;

/**
 * 实现简单，
 * 枚举本身就是单例模式，由JVM从根本上提供保障，避免通过反射和反序列化的漏洞
 */
public enum EnumSingleton {
    // 定义了一个枚举元素，它就代表了一实实例，本身就是单例
    INSTANCE;
}
