package com.dongfang.advanced.jvm.memorystructure;

import org.junit.Test;

/**
 * StringTable特性
 * 	1、常量池中的字符串仅是符号，第一次用到时才变为对象
 * 	2、利用串池的机制，来避免创建重复的字符串对象
 * 	3、字符串变量的拼接原理是StringBuilder
 * 	4、字符串常量拼接的原理是编译期优化
 * 	5、可以使用intern方法，主动将不在串池中的字符串对象放入串池
 * 		将这个字符串对象尝试放入串池，如果有则并不会放入，如果没有则放入串池，会把串池中的对象返回
 * 		StringTable在堆中1.8
 */
public class String的Intern方法 {
    @Test
    public void testIntern() {
        // a b 中串池中，new出的在堆中 s是由StringBuilder拼接而成
        String s = new String("a") + new String("b");
        // ab仅存在于堆中，不在串池中

        // 将字符串对象尝试放入串池中，如果有，不放入，没有，不放入，
        // 者会将串池中对象返回
        String intern = s.intern();
        boolean b = intern == "ab";
        System.out.println("b = " + b);
        System.out.println(intern == "ab");
        // 调用后，原来的s 也指向串池中的对象，可减少重复
        System.out.println(s == intern);
    }

    @Test
    public void testInternAndHeap() {
        String x = "ab";
        String s = new String("a") + new String("b");
        // 串池中已经有ab ，因此不会再创建，直接返回串池中的对象
        String intern = s.intern();
        System.out.println(x == s);
        System.out.println(intern == x);
    }
}
