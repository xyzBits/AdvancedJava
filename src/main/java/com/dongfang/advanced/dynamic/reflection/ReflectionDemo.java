package com.dongfang.advanced.dynamic.reflection;

import org.junit.Test;

/**
 * 动态语言     运行时改变程序的结构
 *      程序运行时，可以改变程序结构或者变量类型，典型的python ruby javascript
 *      C C++ JAVA不是动态语言，但是Java有一定的动态性，我们可以利用反射机制，字节码操作获得类似动态语言的特性
 *      Java是准动态语言
 *
 *      反制机制
 *          指的是可以在运行时加载一些编译期间完全未知的类
 *          --程序在运行状态中，可以动态加载一个只有名称的类，对于任意一个已加载的类，能够知道
 *            这个类中所有的属性和方法，对于一个对象，我们能够调用它的任意一个方法和属性
 *            Class clazz = Class.forName("java.lang.String);
 *            加载完类之后，在堆内存中，就产生了一个Class类型的对象，一个类只有一个Class对象，这个对象包含了完整的类的结构信息，
 *            我们可以通过这个对象看到类的结构，这个对象就像一面镜子，透过这个镜子看到类的纸醉金迷，形象地称为反射
 *
 *
 *
 *         反射的作用：
 *              1、动态加载类、动态获取类的信息（属性、方法、构造器）
 *              2、动态构造对象
 *              3、动态调用类和对象的任意方法、构造器
 *              4、动态调用和处理属性
 *              5、获取泛型信息
 *              6、处理注解
 */
public class ReflectionDemo {

    /**
    * 一个类被 加载后，jvm会创建一个此类的Class对象，这个对象中封装了类的整个结构信息
     * 一个类只对应一个Class对象
     *
     * 创建Class对象的方法
     *      1、全类名：Class.forName("全类名")
     *      2、类型.class String.class
     *      3、对象.getClass()   "Hello World".class
     *      4、基本类型，数组（与维数有关） void
    * */
    @Test
    public void testGetClassObject() {
        String path = "com.dongfang.advanced.dynamic.reflection.User";
        try {
            // 1、全类名
            Class<?> clazz = Class.forName(path);
            System.out.println("clazz = " + clazz);
            System.out.println("Class.forName(path).equals(clazz) = " + Class.forName(path).equals(clazz));

            // 2、类型
            Class<String> stringClass = String.class;

            // 3、对象
            Class<? extends String> aClass = "Hello World".getClass();
            System.out.println("stringClass.equals(aClass) = " + stringClass.equals(aClass));


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


































}
