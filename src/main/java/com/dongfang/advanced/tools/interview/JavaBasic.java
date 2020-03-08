package com.dongfang.advanced.tools.interview;

import org.junit.Test;

public class JavaBasic {
    @Test
    public void testChar() {
        char ch = '东';
        System.out.println(ch + 1);
    }

    @Test
    public void testStringBuilder() {
        StringBuilder sb = new StringBuilder(100);

    }

    class A {
        public A(int a) {

        }
    }
    class B extends A{
        public B() {
            super(1);

        }
    }
}
