package com.dongfang.advanced.designpatterns.creational.factory;

import org.junit.Test;

/**
 * 工厂方法，涉及四个类
 *      产品接口
 *      产品实现
 *
 *      工厂接口
 *      工厂实现
 *
 *      工厂方法模式是简单工厂的仅一步深化， 在工厂方法模式中，我
 *      们不再提供一个统一的工厂类来创建所有的对象，而是针对不同的对象提供不同的工厂。
 *      也就是说每个对象都有一个与之对应的工厂。
 */
public class FactoryMethod {
    //    抽象产品类:
    public abstract class Video {
        public abstract void produce();
    }

    //    具体产品类（ConcreteProduct）
    public class PythonVideo extends Video {
        @Override
        public void produce() {
            System.out.println("Python课程视频");
        }
    }

    public class JavaVideo extends Video {
        @Override
        public void produce() {
            System.out.println("Java课程视频");
        }
    }

    //    抽象工厂类：
    public abstract class VideoFactory {
        public abstract Video getVideo();
    }

    //    具体工厂类:
    public class PythonVideoFactory extends VideoFactory {
        @Override
        public Video getVideo() {
            return new PythonVideo();
        }
    }

    public class JavaVideoFactory extends VideoFactory {
        @Override
        public Video getVideo() {
            return new JavaVideo();
        }
    }

    /**
     * 如果有新的产品 GoLangVideo 让其继承 Video
     *          新建GoLangVideoFactory 继承VideoFactory，不修改Python Java的实现类和工厂类，可扩展性好
     */

    //    客户端调用：
    @Test
    public void testFactoryMethod() {
        VideoFactory videoFactory = new PythonVideoFactory();
        Video video = videoFactory.getVideo();
        video.produce();
    }
    /**
     * 简单工厂在源码中的使用--Collection：
     Collection(抽象工厂)：
     Iterator<E> iterator();
     ArrayList(具体工厂）：
     public Iterator<E> iterator() {
     return new Itr();
     }
     Iterator(抽象产品）：
     public interface Iterator<E> {
     boolean hasNext();

     E next();

     default void remove() {
     throw new UnsupportedOperationException("remove");
     }
     default void forEachRemaining(Consumer<? super E> action) {
     Objects.requireNonNull(action);
     while (hasNext())
     action.accept(next());
     }
     }
     Itr（具体产品）：

     private class Itr implements Iterator<E> {
     int cursor;       // index of next element to return
     int lastRet = -1; // index of last element returned; -1 if no such
     int expectedModCount = modCount;

     Itr() {}

     public boolean hasNext() {
     return cursor != size;
     }
     '省略代码...'
     }
     */
}
