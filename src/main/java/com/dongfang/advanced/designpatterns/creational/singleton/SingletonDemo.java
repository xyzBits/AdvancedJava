package com.dongfang.advanced.designpatterns.creational.singleton;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

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

    /**
     * 反射和反序列化破解单例
     */
    @Test
    public void testReflectSingleton() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> aClass = Class.forName("com.dongfang.advanced.designpatterns.creational.singleton.LazySingleton");

        Constructor<?> constructor = aClass.getDeclaredConstructor(null);
        constructor.setAccessible(true);
        LazySingleton instance = (LazySingleton) constructor.newInstance();
        System.out.println("instance = " + instance);


        System.out.println("LazySingleton.getInstance() = " + LazySingleton.getInstance());

    }

    @Test
    public void testSerializable() throws IOException, ClassNotFoundException {
        LazySingleton instance = LazySingleton.getInstance();
//        FileOutputStream fos = new FileOutputStream("D:\\lidf\\entrance\\object.txt");
//        ObjectOutputStream oos = new ObjectOutputStream(fos);
//        oos.writeObject(instance);
//        oos.close();
//        fos.close();

        FileInputStream fis = new FileInputStream("D:\\lidf\\entrance\\object.txt");
        ObjectInputStream ois = new ObjectInputStream(fis);
        LazySingleton fileObject = (LazySingleton) ois.readObject();

        // 反序列化破解单例
        System.out.println("(instance == fileObject) = " + (instance == fileObject));

    }
}
