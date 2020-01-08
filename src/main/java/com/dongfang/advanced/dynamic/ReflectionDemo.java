package com.dongfang.advanced.dynamic;

import org.junit.Test;

import java.util.Arrays;
import java.util.Date;

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

    private static String path = "com.dongfang.advanced.dynamic.reflection.User";
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
    public void testGetClassObject() throws ClassNotFoundException {
        // 1、全类名
        Class<?> clazz = Class.forName(path);
        System.out.println("clazz = " + clazz);
        System.out.println("Class.forName(path).equals(clazz) = " + Class.forName(path).equals(clazz));

        // 2、类型
        Class<String> stringClass = String.class;

        // 3、对象
        Class<? extends String> aClass = "Hello World".getClass();
        System.out.println("stringClass.equals(aClass) = " + stringClass.equals(aClass));
    }

    /**
     * 反射API，获取类的信息（类的名字、属性、方法、构造器）
     * getDeclaredXxx()获取类中声明的构造器，方法，获取指定的方法，要传方法名，参数类型.class
     * getXXX()         获取类中和父类中代开的构造器方法，获取指定方法，要传方法名，参数类型.class
     */
    @Test
    public void testGetClassInformation() throws ClassNotFoundException, NoSuchFieldException, NoSuchMethodException {
        Class<?> aClass = Class.forName(path);
        // 1、包与包名
        System.out.println("aClass.getPackage() = " + aClass.getPackage());
        System.out.println("aClass.getPackageName() = " + aClass.getPackageName());

        // 2、类名
        System.out.println("aClass.getName() full name = " + aClass.getName());
        System.out.println("aClass.getSimpleName() = " + aClass.getSimpleName());

        // 3、属性
        System.out.println("aClass.getDeclaredFields() = " + Arrays.toString(aClass.getDeclaredFields()));
        System.out.println("aClass.getFields() = " + Arrays.toString(aClass.getFields())); // 只能获取public的属性
        System.out.println("aClass.getDeclaredField(\"id\") = " + aClass.getDeclaredField("id"));

        // 4、构造器
        System.out.println("aClass.getDeclaredConstructors() = " + Arrays.toString(aClass.getDeclaredConstructors()));
        System.out.println("aClass.getConstructors() = " + Arrays.toString(aClass.getConstructors()));
        System.out.println("aClass.getDeclaredConstructor(int.class) = " + aClass.getDeclaredConstructor(int.class));

        // 5、方法
        System.out.println("aClass.getDeclaredMethods() = " + Arrays.toString(aClass.getDeclaredMethods())); // 类中声明的所有方法
        System.out.println("aClass.getMethods() = " + Arrays.toString(aClass.getMethods())); // 公开方法，包含父类的
        System.out.println("aClass.getDeclaredMethod(\"testPrivateMethod\", Date.class) = " + aClass.getDeclaredMethod("testPrivateMethod", Date.class));
    }

    @Test
    public void testUseClassInformation() {

    }

}
