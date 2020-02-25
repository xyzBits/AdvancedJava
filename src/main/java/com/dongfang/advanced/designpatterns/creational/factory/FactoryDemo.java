package com.dongfang.advanced.designpatterns.creational.factory;


import com.dongfang.advanced.designpatterns.creational.factory.model.Audi;
import com.dongfang.advanced.designpatterns.creational.factory.model.Car;
import com.dongfang.advanced.designpatterns.creational.factory.model.Toyota;
import org.junit.Test;

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
