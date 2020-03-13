package com.dongfang.advanced.concurrent.share;

import java.util.ArrayList;
import java.util.List;

/**
 * 线程安全，多个线程调用同一个实例的某个方法时，是线程安全的
 *
 * 线程安全类的单个方法是线程安全的，但是组合起来可能不是线程安全的
 */
public class 继承导致线程不安全 {
    static class SafeThread {
        public void method1(int loop) {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < loop; i++) {
                System.out.println("i = " + i);
                method2(list);
                method3(list);
            }
        }

        public void method2(List<String> list) {
            list.add("1");
        }

        // 私有修改符可以保护线程安全，限制了子类不能重写
        // 或者加final，防止子类重写
        // 修饰符保护方法的线程安全
        // 不想子类影响你的行为，加final
        // private 或者 final提供安全的意义所在，
        public void method3(List<String> list) {
            list.remove(0);
        }
    }

    static class SafeThreadSubClass extends SafeThread {
        // public后，局部变量的引用暴露给其他线程，导致线程不安全
        @Override
        public void method2(List<String> list) {
            new Thread(() -> {
                try {
                    // Exception in thread "Thread-0" Exception in thread "Thread-1" Exception in thread "Thread-2" list.size() = 0
                    System.out.println("list.size() = " + list.size());
                    list.add("1");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }).start();
        }
    }

    public static void main(String[] args) {
        SafeThreadSubClass safeThreadSubClass = new SafeThreadSubClass();
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                safeThreadSubClass.method1(2000);
            }).start();
        }
    }
}