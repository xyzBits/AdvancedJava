package com.dongfang.advanced.designpatterns.creational.factory;

import com.dongfang.advanced.designpatterns.creational.factory.model.Audi;
import com.dongfang.advanced.designpatterns.creational.factory.model.Car;
import com.dongfang.advanced.designpatterns.creational.factory.model.Toyota;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 简单工厂模式：
 * 　　适用场景：工厂类负责创建的对象较少，客户端只关心传入工厂类的参数，对于如何创建对象的逻辑不关心
 * 　　缺点：如果要新加产品，就需要修改工厂类的判断逻辑，违背软件设计中的开闭原则，且产品类多的话，就会使得简单工厂类比较复杂
 *
 * @param <T> 简单工厂模式属于创建型模式又叫做静态工厂方法模式，它属于类创建型模式。在简单工厂模式中，可以根据参数的不同返回不同类的实例。
 *            简单工厂模式专门定义一个类来负责创建其他类的实例，被创建的实例通常都具有共同的父类。
 */
public class SimplyFactory<T> {
    public static Car createCar(String type) {
        Car car = null;
        if (type.equals("Audi")) {
            car = new Audi();
        } else if (type.equals("Toyota")) {
            car = new Toyota();
        }
        // 增加新的产品需要修改工厂类的判断逻辑
        return car;
    }


    // 改进版，使用反射
    // 这样当我们在新增产品的时候就不在需要修改工厂类了。
    public static <T> T createInstance(String className) throws ClassNotFoundException {
        Class<T> clazz = (Class<T>) Class.forName(className);
        Constructor<T> declaredConstructor = null;
        T target = null;
        try {
            declaredConstructor = clazz.getConstructor();
            target = declaredConstructor.newInstance();
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return target;
    }


    public static <T> T createInstance(Class<T> clazz) {
        Constructor<T> declaredConstructor = null;
        T target = null;
        try {
            declaredConstructor = clazz.getConstructor();
            target = declaredConstructor.newInstance();
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return target;
    }
}
