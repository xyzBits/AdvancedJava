package com.dongfang.advanced.designpatterns.creational.singleton;

import org.junit.Test;

public class SingletonDemo {
    @Test
    public void testStaticSingleton() {
        System.out.println("hello");
        System.out.println("StaticBlockSingleton.getInstance() = " + StaticBlockSingleton.getInstance());
    }

    @Test
    public void testStaticInnerClass() {
        System.out.println("hello");
        System.out.println("StaticInnerClassSingleton.getInstance() = " + StaticInnerClassSingleton.getInstance());
    }

    @Test
    public void testEnumSingleton() {
        System.out.println("EnumSingleton.INSTANCE = " + EnumSingleton.INSTANCE.ordinal());
    }
}
