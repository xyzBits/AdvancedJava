package com.dongfang.advanced.designpatterns.creational.factory;


import com.dongfang.advanced.designpatterns.creational.factory.model.Audi;
import com.dongfang.advanced.designpatterns.creational.factory.model.Car;
import com.dongfang.advanced.designpatterns.creational.factory.model.Toyota;
import org.junit.Test;

/**
 * 设计模式--工厂方法模式
 * https://www.jianshu.com/p/6dfb5b66d088
 */
public class FactoryDemo {
    @Test
    public void newVsFactory() throws ClassNotFoundException {
        Car audi = new Audi();
        audi.run();
        Car toyota = SimplyFactory.createInstance(Toyota.class);
        toyota.run();
        Audi instance = SimplyFactory.createInstance(Audi.class.getName());
        instance.run();
    }
}
